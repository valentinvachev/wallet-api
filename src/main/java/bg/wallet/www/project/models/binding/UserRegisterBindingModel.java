package bg.wallet.www.project.models.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {
    @Size(min = 2,message = "Username should be at least 2 symbols")
    @NotNull
    private String username;
    @Pattern(regexp = "^.*(?=.{6,})(?=.*\\d)(?=.*[a-zA-Z]).*$",message = "Password should be at least 6 symbols with letters and digits")
    @NotNull
    private String password;
    private String confirmPassword;
    @Email(message = "Email is not valid format")
    @NotNull
    private String email;

    public UserRegisterBindingModel(String username, String password, String confirmPassword, String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
