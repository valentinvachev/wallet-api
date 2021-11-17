package bg.wallet.www.project.models;

import bg.wallet.www.project.enums.TransactionType;

import javax.persistence.*;


@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    @ManyToOne
    private Event event;
    @ManyToOne
    private Category category;

    public Category getCategory() {
        return category;
    }

    public Transaction setCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public Transaction setName(String name) {
        this.name = name;
        return this;
    }

    public TransactionType getType() {
        return type;
    }

    public Transaction setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public Event getEvent() {
        return event;
    }

    public Transaction setEvent(Event event) {
        this.event = event;
        return this;
    }
}
