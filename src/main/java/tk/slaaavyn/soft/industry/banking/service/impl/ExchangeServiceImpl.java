package tk.slaaavyn.soft.industry.banking.service.impl;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;
import tk.slaaavyn.soft.industry.banking.model.ExchangeRate;
import tk.slaaavyn.soft.industry.banking.service.ExchangeService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final Map<CurrencyType, ExchangeRate> exchangeRates;
    private final CurrencyType baseCurrency = CurrencyType.UAH;

    public ExchangeServiceImpl() {
        this.exchangeRates = new EnumMap<>(CurrencyType.class);
    }

    @Override
    public BigDecimal exchange(CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) throws Exception {
        if (fromCurrency == toCurrency) {
            throw new Exception("from and to currencies should be different");
        }

        ExchangeRate fromExchangeRate = exchangeRates.get(fromCurrency);
        ExchangeRate toExchangeRate = exchangeRates.get(toCurrency);

        // conversion TO base currency
        if (fromExchangeRate != null && fromExchangeRate.getBase_ccy() == toCurrency) {
            return exchangeToBaseCurrency(fromExchangeRate, amount);
        }

        // conversion FROM base currency
        if (toExchangeRate != null && exchangeRates.get(toCurrency).getBase_ccy() == fromCurrency) {
            return exchangeFromBaseCurrency(toExchangeRate, amount);
        }

        // double conversion
        ExchangeRate exchangeRate = fromExchangeRate;
        while (exchangeRate.getCcy() != toCurrency) {
            amount = exchange(exchangeRate.getCcy(), exchangeRate.getBase_ccy(), amount);

            if(exchangeRates.get(exchangeRate.getBase_ccy()) != null) {
                exchangeRate = exchangeRates.get(exchangeRate.getBase_ccy());
            } else {
                exchangeRate = exchangeRates.get(toCurrency);
            }
        }

        return exchange(exchangeRate.getBase_ccy(), exchangeRate.getCcy(), amount);
    }

    private BigDecimal exchangeToBaseCurrency(ExchangeRate exchangeRate, BigDecimal amount) {
        return amount.multiply(exchangeRate.getBuy());
    }

    private BigDecimal exchangeFromBaseCurrency(ExchangeRate exchangeRate, BigDecimal amount) {
        return amount.divide(exchangeRate.getSale(),  BigDecimal.ROUND_DOWN);
    }

    @Scheduled(fixedRate = 300000)
    private void updateExchangeRates() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ExchangeRate[]> response =
                restTemplate.exchange(url, HttpMethod.GET, request, ExchangeRate[].class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // parse response
            for (ExchangeRate exchangeRate : response.getBody()) {
                if(exchangeRate.getBase_ccy() == baseCurrency) {
                    exchangeRates.put(exchangeRate.getCcy(), exchangeRate);
                }
            }
        } else {
            initExchangeRatesDefaultValues();
        }
    }

    // TODO: this function just for test. In real life, rates gives only online.
    private void initExchangeRatesDefaultValues() {
        ExchangeRate eur = new ExchangeRate(
                CurrencyType.EUR,
                CurrencyType.UAH,
                BigDecimal.valueOf(30.00),
                BigDecimal.valueOf(31.00));
        exchangeRates.put(CurrencyType.EUR, eur);

        ExchangeRate usd = new ExchangeRate(
                CurrencyType.USD,
                CurrencyType.UAH,
                BigDecimal.valueOf(27.50),
                BigDecimal.valueOf(28.00));
        exchangeRates.put(CurrencyType.USD, usd);

        ExchangeRate rur = new ExchangeRate(
                CurrencyType.RUR,
                CurrencyType.UAH,
                BigDecimal.valueOf(0.36),
                BigDecimal.valueOf(0.39));
        exchangeRates.put(CurrencyType.RUR, rur);
    }
}
