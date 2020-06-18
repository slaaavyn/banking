package tk.slaaavyn.soft.industry.banking.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.slaaavyn.soft.industry.banking.dto.user.UpdateUserPasswordDto;
import tk.slaaavyn.soft.industry.banking.exceptions.ConflictException;
import tk.slaaavyn.soft.industry.banking.exceptions.NotFoundException;
import tk.slaaavyn.soft.industry.banking.model.Customer;
import tk.slaaavyn.soft.industry.banking.model.Role;
import tk.slaaavyn.soft.industry.banking.model.User;
import tk.slaaavyn.soft.industry.banking.repostitory.CustomerRepository;
import tk.slaaavyn.soft.industry.banking.repostitory.UserRepository;
import tk.slaaavyn.soft.industry.banking.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, CustomerRepository customerRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User incomingUser) {
        if(userRepository.findUserByEmail(incomingUser.getEmail()) != null) {
            throw new ConflictException("User with email: " + incomingUser.getEmail() + " already exist");
        }

        incomingUser.setPassword(passwordEncoder.encode(incomingUser.getPassword()));

        if(incomingUser.getRole() == Role.ROLE_USER) {
            return customerRepository.save(new Customer(incomingUser));
        }

        return userRepository.save(incomingUser);
    }

    @Override
    public User getUser(long userId) {
        User user = userRepository.findUserById(userId);

        if(user == null) {
            throw new NotFoundException("user with id: " + userId + " not found");
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);

        if(user == null) {
            throw new NotFoundException("user with email: " + email + " not found");
        }

        return user;
    }

    @Override
    public List<User> getAllAdmins() {
        return userRepository.findAllByRole(Role.ROLE_ADMIN);
    }

    @Override
    public List<User> getAllCustomers() {
        return userRepository.findAllByRole(Role.ROLE_USER);
    }

    @Override
    public boolean updatePassword(long userId, UpdateUserPasswordDto passwordDto) {
        return false;
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.delete(getUser(userId));
    }
}
