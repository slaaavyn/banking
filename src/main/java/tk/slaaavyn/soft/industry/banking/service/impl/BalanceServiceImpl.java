package tk.slaaavyn.soft.industry.banking.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.soft.industry.banking.exceptions.ApiRequestException;
import tk.slaaavyn.soft.industry.banking.exceptions.ConflictException;
import tk.slaaavyn.soft.industry.banking.exceptions.NotFoundException;
import tk.slaaavyn.soft.industry.banking.model.Balance;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;
import tk.slaaavyn.soft.industry.banking.model.Role;
import tk.slaaavyn.soft.industry.banking.model.User;
import tk.slaaavyn.soft.industry.banking.repostitory.BalanceRepository;
import tk.slaaavyn.soft.industry.banking.service.BalanceService;
import tk.slaaavyn.soft.industry.banking.service.ExchangeService;
import tk.slaaavyn.soft.industry.banking.service.UserService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final UserService userService;
    private final ExchangeService exchangeService;

    public BalanceServiceImpl(BalanceRepository balanceRepository, UserService userService,
                              ExchangeService exchangeService) {
        this.balanceRepository = balanceRepository;
        this.userService = userService;
        this.exchangeService = exchangeService;
    }

    @Override
    public Balance create(Long userId, CurrencyType currencyType) {
        User user = userService.getUser(userId);

        if(user.getRole() != Role.ROLE_USER) {
            throw new ApiRequestException("this user is not a customer");
        }

        if(balanceRepository.findByUser_IdAndCurrencyType(user.getId(), currencyType) != null) {
            throw new ConflictException("balance with currency type: " + currencyType + " already exist in this customer");
        }

        Balance balance = new Balance();
        balance.setUser(user);
        balance.setCurrencyType(exchangeService.getExchangeRate(currencyType).getCurrencyType());
        balance.setDeposit(BigDecimal.ZERO);

        return balanceRepository.save(balance);
    }

    @Override
    public Balance getById(long balanceId) {
        Balance balance = balanceRepository.findById(balanceId);

        if (balance == null) {
            throw new NotFoundException("balance with id: " + balanceId + " not found");
        }

        return balance;
    }

    @Override
    public List<Balance> getALlCustomerBalances(long userId) {
        return balanceRepository.findAllByUser_Id(userId);
    }

    @Override
    public void makeWithdraw(long balanceId, BigDecimal amount) {
        Balance balance = getById(balanceId);

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new ApiRequestException("amount should not be 0");
        }

        if(balance.getDeposit().compareTo(amount) < 0) {
            throw new ApiRequestException("insufficient funds in the account");
        }

        balance.setDeposit(amount.compareTo(BigDecimal.ZERO) < 0
                ? balance.getDeposit().add(amount)
                : balance.getDeposit().subtract(amount));

        balanceRepository.save(balance);
    }

    @Override
    public void makeDeposit(long balanceId, BigDecimal amount) {
        Balance balance = getById(balanceId);

        if(balance.getDeposit().compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new ApiRequestException("amount must be greater than 0");
        }

        balance.setDeposit(balance.getDeposit().add(amount));

        balanceRepository.save(balance);
    }

    @Override
    public void delete(Long balanceId) {
        Balance balance = getById(balanceId);

        if (balance.getDeposit().compareTo(BigDecimal.ZERO) > 0) {
            throw new ApiRequestException("balance with id: " + balanceId + " not empty");
        }

        balanceRepository.delete(balance);
    }
}
