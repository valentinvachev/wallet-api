package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote,Long> {
    Quote findQuoteByMain(Boolean isMain);
}
