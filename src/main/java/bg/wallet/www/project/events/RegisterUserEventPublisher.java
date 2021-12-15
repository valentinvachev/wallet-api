package bg.wallet.www.project.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegisterUserEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public RegisterUserEventPublisher(ApplicationEventPublisher applicationEventPublisher1) {
        this.applicationEventPublisher = applicationEventPublisher1;
    }

    public void publishEvent(String userEmail, LocalDateTime registerTime) {
        RegisterUserEvent registerUserEvent = new RegisterUserEvent(userEmail,registerTime);
        this.applicationEventPublisher.publishEvent(registerUserEvent);
    }
}
