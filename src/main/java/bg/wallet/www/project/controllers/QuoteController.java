package bg.wallet.www.project.controllers;

import bg.wallet.www.project.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("")
    public ResponseEntity<?> getQuotes (@RequestParam(required = false) String main) {

        if ("true".equals(main)) {
            return ResponseEntity.ok().body(this.quoteService.findMain());
        }

        return ResponseEntity.ok().body(this.quoteService.findAll());
    }
}
