package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findByName(String name);
}
