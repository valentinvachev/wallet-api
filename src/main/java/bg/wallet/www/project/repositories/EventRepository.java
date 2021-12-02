package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.Event;
import bg.wallet.www.project.models.view.EventActiveViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    Optional<Event> findByName(String name);
    List<Event> findEventByEndDateGreaterThanEqual(LocalDate today);
}
