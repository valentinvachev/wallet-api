package bg.wallet.www.project.models.view;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionLastViewModel {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String category;
    private String categoryType;
    private String wallet;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public TransactionLastViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public TransactionLastViewModel setCategoryType(String categoryType) {
        this.categoryType = categoryType;
        return this;
    }

    public String getName() {
        return name;
    }

    public TransactionLastViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionLastViewModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TransactionLastViewModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getWallet() {
        return wallet;
    }

    public TransactionLastViewModel setWallet(String wallet) {
        this.wallet = wallet;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TransactionLastViewModel setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
