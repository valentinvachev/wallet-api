package bg.wallet.www.project.web;
import bg.wallet.www.project.models.Event;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.EventsBindingModel;
import bg.wallet.www.project.repositories.EventRepository;
import bg.wallet.www.project.repositories.RoleRepository;
import bg.wallet.www.project.repositories.TransactionRepository;
import bg.wallet.www.project.repositories.UserRepository;
import bg.wallet.www.project.services.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static bg.wallet.www.project.web.Utils.asJsonString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void cleanUp() {
        this.transactionRepository.deleteAll();
        this.eventRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void createEventValid() throws Exception {

        EventsBindingModel eventsBindingModel = new EventsBindingModel();
        eventsBindingModel
                .setName("TEST EVENT")
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now().plusDays(2));

        this.mockMvc.perform(post("/api/events")
                .content(asJsonString(eventsBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void createEventInvalidDates() throws Exception {

        EventsBindingModel eventsBindingModel = new EventsBindingModel();
        eventsBindingModel
                .setName("TEST EVENT")
                .setStartDate(LocalDate.now().plusDays(2))
                .setEndDate(LocalDate.now());

        this.mockMvc.perform(post("/api/events")
                .content(asJsonString(eventsBindingModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void getAllEvents() throws Exception {

        this.mockMvc.perform(get("/api/events")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void getAllActiveEvents() throws Exception {

        this.mockMvc.perform(get("/api/events")
                .param("active","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void getAllActiveEventsSize1() throws Exception {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        authorities.add(new SimpleGrantedAuthority("USER"));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("test@abv.bg",null,authorities);


        this.mockMvc.perform(get("/api/events")
                .param("active","true")
                .principal(authenticationToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
