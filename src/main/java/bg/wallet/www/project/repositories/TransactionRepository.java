package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findTop5ByUser_IdOrderByCreatedAtDesc(Long userId);
    List<Transaction> findAllByWallet_IdOrderByCreatedAtDesc(Long walletId);
    List<Transaction> findTransactionByWallet_IdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(Long id, LocalDateTime startDate, LocalDateTime endDate);
}
