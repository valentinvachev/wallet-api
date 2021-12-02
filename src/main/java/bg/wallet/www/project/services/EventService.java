package bg.wallet.www.project.services;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Event;
import bg.wallet.www.project.models.binding.EventsBindingModel;
import bg.wallet.www.project.models.view.EventActiveViewModel;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    Long save(EventsBindingModel eventsBindingModel) throws InvalidInputException, DuplicateEntityException;
    Event findById(Long id);
    List<EventActiveViewModel> findAll();
    Event findByName(String name);
    List<EventActiveViewModel> findActiveEvents();
}
