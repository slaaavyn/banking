package tk.slaaavyn.soft.industry.banking.service;

import tk.slaaavyn.soft.industry.banking.model.CurrencyType;
import tk.slaaavyn.soft.industry.banking.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction makeDeposit(long balanceId, CurrencyType transactionCurrencyType, BigDecimal amount);

    Transaction makeWithdraw(long executorId, long balanceId, CurrencyType transactionCurrencyType, BigDecimal amount);

    Transaction getById(long transactionId);

    List<Transaction> getTransactions(long balanceOwnerId, long balanceId);
}
