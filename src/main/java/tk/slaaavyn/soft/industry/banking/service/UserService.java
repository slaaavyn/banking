package tk.slaaavyn.soft.industry.banking.service;

import tk.slaaavyn.soft.industry.banking.dto.user.UpdateUserPasswordDto;
import tk.slaaavyn.soft.industry.banking.model.User;

import java.util.List;

public interface UserService {
    User create(User incomingUser);

    User getUser(long userId);

    User getUserByEmail(String email);

    List<User> getAllAdmins();

    List<User> getAllCustomers();

    void updatePassword(long userId, UpdateUserPasswordDto passwordDto);

    void deleteUser(long userId);
}
