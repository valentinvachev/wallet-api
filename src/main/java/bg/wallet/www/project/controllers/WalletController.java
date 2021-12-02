package bg.wallet.www.project.controllers;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.binding.CategoryBindingModel;
import bg.wallet.www.project.models.binding.WalletBindingModel;
import bg.wallet.www.project.models.binding.WalletEditBindingModel;
import bg.wallet.www.project.services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> editWallet(HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody WalletEditBindingModel walletEditBindingModel) throws InvalidInputException, EntityNotFoundException {

        Map<String,String> bodyResponse = new HashMap<>();

        //TODO if user that edits owns the wallet

        bodyResponse.put("edited",String.valueOf(this.walletService.editName(id,walletEditBindingModel,"admin@abv.bg")));

        return ResponseEntity.ok().body(bodyResponse);
    }

    @GetMapping("")
    public ResponseEntity<?> getWallets(HttpServletRequest request, @RequestParam(required = false) String total) {

        if (("true").equals(total)) {
            return ResponseEntity.ok().body(this.walletService.findTotal());
        }

        int a = 1;
        return ResponseEntity.ok().body(this.walletService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWallet(HttpServletRequest request, @PathVariable Long id, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) throws EntityNotFoundException, InvalidInputException {

        int a = 1;

        if (startDate != null && endDate != null) {
            ZonedDateTime zdtStart = ZonedDateTime.parse(startDate);
            LocalDateTime ldtStart = zdtStart.toLocalDateTime();

            ZonedDateTime zdtEnd = ZonedDateTime.parse(endDate);
            LocalDateTime ldtEnd = zdtEnd.toLocalDateTime().with(LocalTime.of(23,59));

            return ResponseEntity.ok().body(this.walletService.getWalletReportById(id,ldtStart,ldtEnd));
        }

        return ResponseEntity.ok().body(this.walletService.getWalletById(id));
    }
}
