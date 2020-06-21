package tk.slaaavyn.soft.industry.banking.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.soft.industry.banking.exceptions.ApiRequestException;
import tk.slaaavyn.soft.industry.banking.exceptions.NotFoundException;
import tk.slaaavyn.soft.industry.banking.model.*;
import tk.slaaavyn.soft.industry.banking.repostitory.TransactionRepository;
import tk.slaaavyn.soft.industry.banking.service.BalanceService;
import tk.slaaavyn.soft.industry.banking.service.ExchangeService;
import tk.slaaavyn.soft.industry.banking.service.TransactionService;
import tk.slaaavyn.soft.industry.banking.service.UserService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final BalanceService balanceService;
    private final ExchangeService exchangeService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserService userService,
                                  BalanceService balanceService, ExchangeService exchangeService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.balanceService = balanceService;
        this.exchangeService = exchangeService;
    }

    @Override
    public Transaction makeDeposit(long balanceId, CurrencyType transactionCurrencyType, BigDecimal amount) {
        Balance balance = balanceService.getById(balanceId);

        Transaction transaction = this.initTransaction(balance, TransactionType.DEPOSIT,
                transactionCurrencyType, amount);
        balanceService.makeDeposit(balanceId, transaction.getCounted());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction makeWithdraw(long executorId, long balanceId,
                                    CurrencyType transactionCurrencyType, BigDecimal amount) {
        Balance balance = balanceService.getById(balanceId);
        User executor = userService.getUser(executorId);

        if(executor.getRole() == Role.ROLE_USER && !balance.getUser().getId().equals(executor.getId())) {
            throw new ApiRequestException("user cannot change the balance that does not belong to him");
        }

        Transaction transaction = this.initTransaction(balance, TransactionType.WITHDRAW,
                transactionCurrencyType, amount);
        balanceService.makeWithdraw(balanceId, transaction.getCounted());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getById(long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId);

        if(transaction == null) {
            throw new NotFoundException("transaction with id: " + transactionId + " no found");
        }

        return transaction;
    }

    @Override
    public List<Transaction> getTransactions(long balanceOwnerId, long balanceId) {
        if (balanceId == 0) {
            return transactionRepository.findAllByBalance_User_IdOrderByDateAsc(balanceOwnerId);
        }

        return transactionRepository.findAllByBalance_User_IdAndBalance_IdOrderByDateAsc(balanceOwnerId, balanceId);
    }

    private Transaction initTransaction(Balance balance,
                                        TransactionType transactionType,
                                        CurrencyType transactionCurrencyType,
                                        BigDecimal amount) {
        ExchangeRate exchangeRate = exchangeService.getExchangeRate(transactionCurrencyType);

        Transaction transaction = new Transaction();

        transaction.setBalance(balance);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionCurrencyType(exchangeRate.getCurrencyType());
        transaction.setAmount(amount);
        transaction.setCounted(this.calculateCountedAmount(balance, exchangeRate.getCurrencyType(), amount));
        transaction.setDeleted(false);
        transaction.setDate(new Date());

        return transaction;
    }

    private BigDecimal calculateCountedAmount(Balance balance,
                                              CurrencyType transactionCurrencyType,
                                              BigDecimal amount) {

        if (!isNeedToExchangeCurrency(transactionCurrencyType, balance.getCurrencyType())) {
            return amount;
        }

        try {
            return exchangeService.exchange(transactionCurrencyType, balance.getCurrencyType(), amount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage());
        }

    }

    private boolean isNeedToExchangeCurrency(CurrencyType incomingCurrency, CurrencyType balanceCurrency) {
        return incomingCurrency != balanceCurrency;
    }
}
