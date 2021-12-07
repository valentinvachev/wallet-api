package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findByNameAndUserEmail(String name,String userEmail);
    @Query("SELECT SUM(w.balance) FROM Wallet w WHERE w.user.email = ?1")
    BigDecimal findSumOfAllWallets(String userEmail);
    Wallet getWalletById(Long id);
    List<Wallet> findAllByUserEmail(String userEmail);
}
