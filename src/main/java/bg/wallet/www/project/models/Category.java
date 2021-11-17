package bg.wallet.www.project.models;

import bg.wallet.www.project.enums.TransactionType;

import javax.persistence.*;


@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    public TransactionType getType() {
        return type;
    }

    public Category setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }
}
