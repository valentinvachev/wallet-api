package bg.wallet.www.project.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegisterUserEventListener {

    @EventListener(RegisterUserEvent.class)
    public void onRegisterUser(RegisterUserEvent event) {
        log.info(String.format("User registration: %s at %s",event.getUserEmail(),event.getRegisterTime()));
    }

}
