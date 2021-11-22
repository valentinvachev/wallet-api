package bg.wallet.www.project.models.service;

import bg.wallet.www.project.models.Category;
import bg.wallet.www.project.models.Event;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionServiceModel {
    private String name;
    private BigDecimal amount;
    private Event event;
    private Category category;
    private Wallet wallet;
    private User user;
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TransactionServiceModel setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getName() {
        return name;
    }

    public TransactionServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionServiceModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Event getEvent() {
        return event;
    }

    public TransactionServiceModel setEvent(Event event) {
        this.event = event;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public TransactionServiceModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public TransactionServiceModel setWallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public User getUser() {
        return user;
    }

    public TransactionServiceModel setUser(User user) {
        this.user = user;
        return this;
    }
}
