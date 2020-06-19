package tk.slaaavyn.soft.industry.banking.service;


import tk.slaaavyn.soft.industry.banking.model.Balance;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;

import java.util.List;

public interface BalanceService {

    Balance create(Long customerId, CurrencyType currencyType);

    Balance getById(long balanceId);

    List<Balance> getALlCustomerBalances(long customerId);

    void delete(Long balanceId);
}
