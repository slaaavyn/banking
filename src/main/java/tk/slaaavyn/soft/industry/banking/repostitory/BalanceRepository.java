package tk.slaaavyn.soft.industry.banking.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.soft.industry.banking.model.Balance;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findById(long id);
    Balance findByUser_IdAndCurrencyType(long userId, CurrencyType currencyType);
    List<Balance> findAllByUser_Id(long userId);
}
