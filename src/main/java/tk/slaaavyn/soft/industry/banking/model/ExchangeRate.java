package tk.slaaavyn.soft.industry.banking.model;

import java.math.BigDecimal;

public class ExchangeRate {
    private CurrencyType ccy;
    private CurrencyType base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;

    public ExchangeRate() {
    }

    public ExchangeRate(CurrencyType ccy, CurrencyType base_ccy, BigDecimal buy, BigDecimal sale) {
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }

    public CurrencyType getCcy() {
        return ccy;
    }

    public void setCcy(CurrencyType ccy) {
        this.ccy = ccy;
    }

    public CurrencyType getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(CurrencyType base_ccy) {
        this.base_ccy = base_ccy;
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
                "ccy=" + ccy +
                ", base_ccy=" + base_ccy +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}
