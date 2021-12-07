package bg.wallet.www.project.controllers;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.binding.TransactionBindingModel;
import bg.wallet.www.project.models.view.TransactionLastViewModel;
import bg.wallet.www.project.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("")
    public ResponseEntity<?> createTransaction (HttpServletRequest request, @Valid @RequestBody TransactionBindingModel transactionBindingModel) throws URISyntaxException, InvalidInputException {

        String userEmail = request.getUserPrincipal().getName();
        Map<String,String> bodyResponse = new HashMap<>();

        bodyResponse.put("created",String.valueOf(this.transactionService.save(transactionBindingModel,userEmail)));

        return ResponseEntity.created(new URI(request.getServletPath())).body(bodyResponse);
    }


    @GetMapping("")
    public ResponseEntity<?> getTransactions(HttpServletRequest request, @RequestParam(required = false) String last) throws URISyntaxException {
        String userEmail = request.getUserPrincipal().getName();
        Map<String,String> bodyResponse = new HashMap<>();

        if (last.equals("true")) {
            List<TransactionLastViewModel> transactionLastViewModelList = this.transactionService.findLastFiveTransactionsOfUser(userEmail);
            return ResponseEntity.ok().body(transactionLastViewModelList);
        }

        return ResponseEntity.ok().body(bodyResponse);
    }
}
