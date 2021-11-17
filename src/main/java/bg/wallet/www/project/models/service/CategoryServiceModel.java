package bg.wallet.www.project.models.service;

import bg.wallet.www.project.enums.TransactionType;

public class CategoryServiceModel {
    private String name;
    private TransactionType type;

    public String getName() {
        return name;
    }

    public CategoryServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public TransactionType getType() {
        return type;
    }

    public CategoryServiceModel setType(TransactionType type) {
        this.type = type;
        return this;
    }
}
