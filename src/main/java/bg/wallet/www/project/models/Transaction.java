package bg.wallet.www.project.models;

import bg.wallet.www.project.enums.TransactionType;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Column(nullable = false)
    @Size(min = 2,message = "Name should be at least 2 symbols")
    private String name;
    @Column(nullable = false)
    private BigDecimal amount;
    @ManyToOne
    private Event event;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Wallet wallet;
    @ManyToOne
    private User user;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User getUser() {
        return user;
    }

    public Transaction setUser(User user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public Transaction setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Event getEvent() {
        return event;
    }

    public Transaction setEvent(Event event) {
        this.event = event;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Transaction setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Transaction setWallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Transaction setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
