package tk.slaaavyn.soft.industry.banking.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.soft.industry.banking.model.Balance;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findById(long id);
    Balance findByCustomer_IdAndCurrencyType(long customerId, CurrencyType currencyType);
    List<Balance> findAllByCustomer_Id(long customerId);
}
