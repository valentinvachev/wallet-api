package bg.wallet.www.project.controllers;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.WalletBindingModel;
import bg.wallet.www.project.services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("")
    public ResponseEntity<?> createWallet(HttpServletRequest request, @Valid @RequestBody WalletBindingModel walletBindingModel) throws URISyntaxException, DuplicateEntityException, InvalidInputException {

        Map<String,String> bodyResponse = new HashMap<>();

        //TODO replace with user from token

        bodyResponse.put("created",String.valueOf(this.walletService.save(walletBindingModel,"admin@abv.bg")));
        bodyResponse.put("user","admin@abv.bg");

        return ResponseEntity.created(new URI(request.getServletPath())).body(bodyResponse);
    }

    @GetMapping("")
    public ResponseEntity<?> getWallets(HttpServletRequest request) {

        return ResponseEntity.ok().body(this.walletService.findAll());
    }
}
