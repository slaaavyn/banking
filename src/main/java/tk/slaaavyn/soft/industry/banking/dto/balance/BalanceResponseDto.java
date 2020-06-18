package tk.slaaavyn.soft.industry.banking.dto.balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.slaaavyn.soft.industry.banking.model.Balance;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceResponseDto {
    private Long id;
    private Long deposit;
    private Long customerId;
    private CurrencyType currencyType;

    public static BalanceResponseDto toDto(Balance balance) {
        BalanceResponseDto dto = new BalanceResponseDto();

        dto.setId(balance.getId());
        dto.setDeposit(balance.getDeposit());
        dto.setCustomerId(balance.getCustomer().getId());
        dto.setCurrencyType(balance.getCurrencyType());

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
                ", customerId=" + customerId +
                ", currencyType=" + currencyType +
                '}';
    }
}
