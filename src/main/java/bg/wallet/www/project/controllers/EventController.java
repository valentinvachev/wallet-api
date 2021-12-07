package bg.wallet.www.project.controllers;

import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.InvalidInputException;
import bg.wallet.www.project.models.binding.EventsBindingModel;
import bg.wallet.www.project.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("")
    public ResponseEntity<?> createEvent(HttpServletRequest request, @Valid @RequestBody EventsBindingModel eventsBindingModel) throws URISyntaxException, InvalidInputException, DuplicateEntityException {
        String userEmail = request.getUserPrincipal().getName();
        Map<String,String> bodyResponse = new HashMap<>();

        bodyResponse.put("created",String.valueOf(this.eventService.save(eventsBindingModel,userEmail)));

        return ResponseEntity.created(new URI(request.getServletPath())).body(bodyResponse);
    }

    @GetMapping("")
    public ResponseEntity<?> getEvents(HttpServletRequest request, @RequestParam(required = false) String active){
        String userEmail = request.getUserPrincipal().getName();

        if ("true".equals(active)) {
            return ResponseEntity.ok().body(this.eventService.findActiveEvents(userEmail));
        }

        return ResponseEntity.ok().body(this.eventService.findAll(userEmail));
    }
}
