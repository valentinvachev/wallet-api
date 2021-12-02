package bg.wallet.www.project.schedule;

import bg.wallet.www.project.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Scheduler {

    private QuoteService quoteService;

    @Autowired
    public Scheduler(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Scheduled(fixedDelay = 120000)
    public void changeMainQuote() {
        this.quoteService.seedQuotes();
        this.quoteService.makeMain();
    }
}
