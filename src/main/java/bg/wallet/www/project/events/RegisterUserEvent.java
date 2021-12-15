package bg.wallet.www.project.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class RegisterUserEvent extends ApplicationEvent {

    private String userEmail;
    private LocalDateTime registerTime;

    public RegisterUserEvent(String userEmail, LocalDateTime registerTime) {
        super(userEmail);
        this.userEmail = userEmail;
        this.registerTime = registerTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public RegisterUserEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public RegisterUserEvent setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
        return this;
    }
}
