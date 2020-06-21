package tk.slaaavyn.soft.industry.banking.service;

import tk.slaaavyn.soft.industry.banking.model.CurrencyType;
import tk.slaaavyn.soft.industry.banking.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeService {
    ExchangeRate getExchangeRate(CurrencyType currencyType);

    List<ExchangeRate> getAllRates();

    BigDecimal exchange(CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) throws Exception;
}
