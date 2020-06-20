package tk.slaaavyn.soft.industry.banking.service;


import tk.slaaavyn.soft.industry.banking.model.Balance;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;

import java.math.BigDecimal;
import java.util.List;

public interface BalanceService {

    Balance create(Long userId, CurrencyType currencyType);

    Balance getById(long balanceId);

    List<Balance> getALlCustomerBalances(long userId);

    void makeWithdraw(long balanceId, BigDecimal amount);

    void makeDeposit(long balanceId, BigDecimal amount);

    void delete(Long balanceId);
}
