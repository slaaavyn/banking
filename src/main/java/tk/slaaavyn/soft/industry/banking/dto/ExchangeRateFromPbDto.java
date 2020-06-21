package tk.slaaavyn.soft.industry.banking.dto;

import tk.slaaavyn.soft.industry.banking.model.CurrencyType;
import tk.slaaavyn.soft.industry.banking.model.ExchangeRate;

import java.math.BigDecimal;

public class ExchangeRateFromPbDto {
    private String ccy;
    private String base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;

    public ExchangeRate fromDto() {
        return new ExchangeRate(CurrencyType.valueOf(ccy), CurrencyType.valueOf(base_ccy), buy, sale);
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(String base_ccy) {
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
        return "ExchangeRateFromPbDto{" +
                "ccy=" + ccy +
                ", base_ccy=" + base_ccy +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}
