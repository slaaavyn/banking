package tk.slaaavyn.soft.industry.banking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tk.slaaavyn.soft.industry.banking.model.Role;
import tk.slaaavyn.soft.industry.banking.model.User;
import tk.slaaavyn.soft.industry.banking.repostitory.UserRepository;

@Component
public class DbInitializer implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    @Value("${default.admin.email}")
    private String defaultAdminEmail;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DbInitializer(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initAdmin();
    }


    private void initAdmin() {
        if (userRepository.findAllByRole(Role.ROLE_ADMIN).size() != 0) {
            return;
        }

        User user = new User();
        user.setEmail(defaultAdminEmail);
        user.setPassword(passwordEncoder.encode(defaultAdminPassword));
        user.setRole(Role.ROLE_ADMIN);
        user.setFirstName("Admin");
        user.setLastName("Admin");
        userRepository.save(user);

        logger.info("ADMIN has been initialized");
    }
}