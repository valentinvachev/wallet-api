package bg.wallet.www.project.services;
import bg.wallet.www.project.models.Event;
import bg.wallet.www.project.repositories.EventRepository;
import bg.wallet.www.project.services.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceTest {

    private Event testEvent;
    private EventRepository eventRepository;
    private ModelMapper modelMapper;

    private final String EVENT_NAME = "TEST_EVENT";
    private final LocalDate START_DATE = LocalDate.now();
    private final LocalDate END_DATE = LocalDate.now().plusDays(1);

    @BeforeEach
    public void init() {

        this.testEvent = (Event) new Event()
                .setName(EVENT_NAME)
                .setStartDate(START_DATE)
                .setEndDate(END_DATE)
                .setId(1L);


        this.eventRepository = mock(EventRepository.class);
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void findAllActive() {

        List<Event> events = new ArrayList<>();
        events.add(this.testEvent);

        when(this.eventRepository.findEventByEndDateGreaterThanEqual(LocalDate.now()))
                .thenReturn(events);


        EventService eventService = new EventServiceImpl(this.eventRepository,this.modelMapper);

        assertEquals(1,eventService.findActiveEvents().size());
    }

    @Test
    public void findAll() {

        List<Event> events = new ArrayList<>();
        events.add(this.testEvent);

        when(this.eventRepository.findAll())
                .thenReturn(events);


        EventService eventService = new EventServiceImpl(this.eventRepository,this.modelMapper);

        assertEquals(1,eventService.findAll().size());
    }
}
