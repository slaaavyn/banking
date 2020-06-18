package tk.slaaavyn.soft.industry.banking.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.soft.industry.banking.model.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findById(long id);
}
