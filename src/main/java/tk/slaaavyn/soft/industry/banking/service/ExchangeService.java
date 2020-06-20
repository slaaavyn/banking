package tk.slaaavyn.soft.industry.banking.service;

import tk.slaaavyn.soft.industry.banking.model.CurrencyType;

import java.math.BigDecimal;

public interface ExchangeService {
    BigDecimal exchange(CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) throws Exception;
}
