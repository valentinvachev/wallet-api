package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findByName(String name);
    @Query("SELECT SUM(w.balance) FROM Wallet w")
    BigDecimal findSumOfAllWallets();
    Wallet getById(Long id);
}
