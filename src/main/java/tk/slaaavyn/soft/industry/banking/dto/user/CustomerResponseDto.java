package tk.slaaavyn.soft.industry.banking.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.slaaavyn.soft.industry.banking.dto.balance.BalanceResponseDto;
import tk.slaaavyn.soft.industry.banking.model.User;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerResponseDto extends UserResponseDto {
    private List<BalanceResponseDto> balanceList;

    public static CustomerResponseDto toDto(User user) {
        CustomerResponseDto dto = new CustomerResponseDto();

        List<BalanceResponseDto> balanceDtoList = new ArrayList<>();
        if (user.getBalanceList() != null) {
            user.getBalanceList().forEach(balance -> balanceDtoList.add(BalanceResponseDto.toDto(balance)));
        }

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
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
