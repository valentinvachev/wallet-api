package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    Event findByNameAndUserEmail(String name,String userEmail);
    List<Event> findEventByEndDateGreaterThanEqualAndUserEmail(LocalDate today, String userEmail);
    List<Event> findAllByUserEmail(String userEmail);
    Event findEventById(Long id);
}
