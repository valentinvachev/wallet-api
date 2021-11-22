package bg.wallet.www.project.models.binding;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TransactionBindingModel {

    @NotNull
    @Size(min = 2,message = "Name should be at least 2 symbols")
    private String name;
    @NotNull
    private BigDecimal amount;
    private Long event;
    @NotNull
    private Long category;
    @NotNull
    private Long wallet;

    public String getName() {
        return name;
    }

    public TransactionBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionBindingModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Long getEvent() {
        return event;
    }

    public TransactionBindingModel setEvent(Long event) {
        this.event = event;
        return this;
    }

    public Long getCategory() {
        return category;
    }

    public TransactionBindingModel setCategory(Long category) {
        this.category = category;
        return this;
    }

    public Long getWallet() {
        return wallet;
    }

    public TransactionBindingModel setWallet(Long wallet) {
        this.wallet = wallet;
        return this;
    }
}
