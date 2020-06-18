package tk.slaaavyn.soft.industry.banking.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.slaaavyn.soft.industry.banking.dto.balance.BalanceResponseDto;
import tk.slaaavyn.soft.industry.banking.model.Customer;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerResponseDto extends UserResponseDto {
    private List<BalanceResponseDto> balanceList;

    public static CustomerResponseDto toDto(Customer customer) {
        CustomerResponseDto dto = new CustomerResponseDto();

        List<BalanceResponseDto> balanceDtoList = new ArrayList<>();
        if (customer.getBalanceList() != null) {
            customer.getBalanceList().forEach(balance -> balanceDtoList.add(BalanceResponseDto.toDto(balance)));
        }

        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setRole(customer.getRole());
        dto.setBalanceList(balanceDtoList);

        return dto;
    }

    public List<BalanceResponseDto> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<BalanceResponseDto> balanceList) {
        this.balanceList = balanceList;
    }

    @Override
    public String toString() {
        return "CustomerUserDtoResponse{" +
                "balanceList=" + balanceList +
                "} " + super.toString();
    }
}
