package tk.slaaavyn.soft.industry.banking.dto.user;

import tk.slaaavyn.soft.industry.banking.model.Role;
import tk.slaaavyn.soft.industry.banking.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateUserRequestDto {

    @NotEmpty(message = "email cannot be empty")
    @NotNull(message = "email cannot be null")
    @Email(message = "email should be valid",
            regexp = "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}")
    private String email;

    @NotEmpty(message = "password cannot be empty")
    @NotNull(message = "password cannot be null")
    private String password;

    @NotEmpty(message = "firstName cannot be empty")
    @NotNull(message = "firstName cannot be null")
    private String firstName;

    @NotEmpty(message = "lastName cannot be empty")
    @NotNull(message = "lastName cannot be null")
    private String lastName;

    @NotNull(message = "role cannot be null")
    private Role role;

    public User fromDto() {
        User user = new User();

        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);

        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CreateUserRequestDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                '}';
    }
}
