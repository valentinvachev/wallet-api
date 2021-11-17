package bg.wallet.www.project.models.binding;

import bg.wallet.www.project.enums.TransactionType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryBindingModel {
    @NotNull
    @Size(min = 1, message = "Category name should be at least 1 symbol")
    private String name;
    @NotNull
    //TODO check if it is valid enum type of the role
    private String type;

    public String getName() {
        return name;
    }

    public CategoryBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public CategoryBindingModel setType(String type) {
        this.type = type;
        return this;
    }
}
