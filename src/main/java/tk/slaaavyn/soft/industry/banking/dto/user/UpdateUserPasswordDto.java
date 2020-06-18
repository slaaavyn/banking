package tk.slaaavyn.soft.industry.banking.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserPasswordDto {

    @NotEmpty(message = "oldPassword cannot be empty")
    @NotNull(message = "oldPassword cannot be null")
    private String oldPassword;

    @NotEmpty(message = "newPassword cannot be empty")
    @NotNull(message = "newPassword cannot be null")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UpdatePasswordDto{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
