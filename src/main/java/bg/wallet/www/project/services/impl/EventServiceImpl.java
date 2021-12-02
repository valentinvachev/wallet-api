package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.Event;
import bg.wallet.www.project.models.binding.EventsBindingModel;
import bg.wallet.www.project.models.service.EventServiceModel;
import bg.wallet.www.project.models.view.EventActiveViewModel;
import bg.wallet.www.project.models.view.TransactionLastViewModel;
import bg.wallet.www.project.repositories.EventRepository;
import bg.wallet.www.project.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EventActiveViewModel> findActiveEvents() {
        List<Event> events = this.eventRepository.findEventByEndDateGreaterThanEqual(LocalDate.now());

        return events.stream()
                .map(e->this.modelMapper.map(e, EventActiveViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long save(EventsBindingModel eventsBindingModel) throws InvalidInputException, DuplicateEntityException {

        Event eventDb = this.findByName(eventsBindingModel.getName());

        if (eventDb != null) {
            throw new DuplicateEntityException("Event with this name already exists");
        }

        if (eventsBindingModel.getStartDate().isAfter(eventsBindingModel.getStartDate())) {
            throw new InvalidInputException("Start date should not be after end date");
        }

        EventServiceModel eventServiceModel = this.modelMapper.map(eventsBindingModel,EventServiceModel.class);
        Event event = this.modelMapper.map(eventServiceModel,Event.class);
        return eventRepository.save(event).getId();
    }

    @Override
    public List<EventActiveViewModel> findAll() {
        List<Event> events = this.eventRepository.findAll();

        return events.stream()
                .map(e->this.modelMapper.map(e, EventActiveViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Event findByName(String name) {
        return this.eventRepository.findByName(name).orElse(null);
    }

    @Override
    public Event findById(Long id) {
        return this.eventRepository.findById(id).orElse(null);
    }
}
