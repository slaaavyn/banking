package tk.slaaavyn.soft.industry.banking.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.soft.industry.banking.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findById(long transactionId);
    List<Transaction> findAllByBalance_User_IdOrderByDateAsc(long balanceId);
    List<Transaction> findAllByBalance_User_IdAndBalance_IdOrderByDateAsc(long balanceOwnerId, long balanceId);
}
