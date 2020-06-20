package tk.slaaavyn.soft.industry.banking.dto.transaction;

import tk.slaaavyn.soft.industry.banking.model.CurrencyType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransactionCreateRequestDto {
    @NotNull(message = "balanceId cannot be null")
    private Long balanceId;

    @NotNull(message = "currencyType cannot be null")
    private CurrencyType currencyType;

    @NotNull(message = "amount cannot be null")
    private BigDecimal amount;

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionCreateRequestDto{" +
                "balanceId=" + balanceId +
                ", currencyType=" + currencyType +
                ", amount=" + amount +
                '}';
    }
}
