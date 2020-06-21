package tk.slaaavyn.soft.industry.banking.model;

import java.math.BigDecimal;

public class ExchangeRate {
    private CurrencyType currencyType;
    private CurrencyType baseCurrencyType;
    private BigDecimal buy;
    private BigDecimal sale;

    public ExchangeRate() {
    }

    public ExchangeRate(CurrencyType currencyType, CurrencyType baseCurrencyType, BigDecimal buy, BigDecimal sale) {
        this.currencyType = currencyType;
        this.baseCurrencyType = baseCurrencyType;
        this.buy = buy;
        this.sale = sale;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public CurrencyType getBaseCurrencyType() {
        return baseCurrencyType;
    }

    public void setBaseCurrencyType(CurrencyType baseCurrencyType) {
        this.baseCurrencyType = baseCurrencyType;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "ccy=" + currencyType +
                ", base_ccy=" + baseCurrencyType +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}
