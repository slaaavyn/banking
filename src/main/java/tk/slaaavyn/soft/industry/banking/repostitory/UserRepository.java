package tk.slaaavyn.soft.industry.banking.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.soft.industry.banking.model.Role;
import tk.slaaavyn.soft.industry.banking.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    User findUserByEmail(String email);
    List<User> findAllByRole(Role role);
}
