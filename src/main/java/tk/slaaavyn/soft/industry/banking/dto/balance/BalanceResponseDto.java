package tk.slaaavyn.soft.industry.banking.dto.balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.slaaavyn.soft.industry.banking.model.Balance;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceResponseDto {
    private Long id;
    private BigDecimal deposit;
    private Long userId;
    private CurrencyType currencyType;

    public static BalanceResponseDto toDto(Balance balance) {
        BalanceResponseDto dto = new BalanceResponseDto();

        dto.setId(balance.getId());
        dto.setDeposit(balance.getDeposit());
        dto.setUserId(balance.getUser().getId());
        dto.setCurrencyType(balance.getCurrencyType());

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return "BalanceResponseDto{" +
                "id=" + id +
                ", deposit=" + deposit +
                ", userId=" + userId +
                ", currencyType=" + currencyType +
                '}';
    }
}
