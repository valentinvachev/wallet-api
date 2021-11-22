package bg.wallet.www.project.models.binding;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class WalletBindingModel {

    @NotNull
    @Size(min = 2,message = "Name should be at least 2 symbols")
    private String name;
    @NotNull
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public WalletBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public WalletBindingModel setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}
