package bg.wallet.www.project.web;
import bg.wallet.www.project.repositories.QuoteRepository;
import bg.wallet.www.project.services.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    QuoteService quoteService;
    @Autowired
    QuoteRepository quoteRepository;

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void getMain() throws Exception {

        this.mockMvc.perform(get("/api/quotes").param("main","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(this.quoteRepository.findQuoteByMain(true).getAuthor()));
    }

    @Test
    @WithMockUser(username = "test@abv.bg",roles = {"USER","ADMIN"})
    public void getAll() throws Exception {

        this.mockMvc.perform(get("/api/quotes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize((int) this.quoteRepository.count())));
    }
}
