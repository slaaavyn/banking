package tk.slaaavyn.soft.industry.banking.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.soft.industry.banking.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
