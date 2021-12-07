package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Event;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.EventsBindingModel;
import bg.wallet.www.project.models.service.EventServiceModel;
import bg.wallet.www.project.models.view.EventActiveViewModel;
import bg.wallet.www.project.repositories.EventRepository;
import bg.wallet.www.project.services.EventService;
import bg.wallet.www.project.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper, UserService userService) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public Long save(EventsBindingModel eventsBindingModel, String userEmail) throws InvalidInputException, DuplicateEntityException {
        User user = this.userService.findByEmail(userEmail);

        if (user == null) {
            throw new InvalidInputException("User does not exist");
        }

        Event eventDb = this.findByNameAndEmail(eventsBindingModel.getName(),userEmail);

        if (eventDb != null) {
            throw new DuplicateEntityException("Event with this name already exists");
        }

        if (eventsBindingModel.getStartDate().isAfter(eventsBindingModel.getEndDate())) {
            throw new InvalidInputException("Start date should not be after end date");
        }

        EventServiceModel eventServiceModel = this.modelMapper.map(eventsBindingModel,EventServiceModel.class);
        Event event = this.modelMapper.map(eventServiceModel,Event.class);
        event.setUser(user);
        return eventRepository.save(event).getId();
    }

    @Override
    public List<EventActiveViewModel> findActiveEvents(String userEmail) {
        List<Event> events = this.eventRepository.findEventByEndDateGreaterThanEqualAndUserEmail(LocalDate.now(),userEmail);

        return events.stream()
                .map(e->this.modelMapper.map(e, EventActiveViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventActiveViewModel> findAll(String userEmail) {
        List<Event> events = this.eventRepository.findAllByUserEmail(userEmail);

        return events.stream()
                .map(e->this.modelMapper.map(e, EventActiveViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Event findByNameAndEmail(String name, String userEmail) {
        return this.eventRepository.findByNameAndUserEmail(name,userEmail);
    }

    @Override
    public Event findById(Long id) {
        return this.eventRepository.findEventById(id);
    }
}
