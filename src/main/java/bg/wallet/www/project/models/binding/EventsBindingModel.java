package bg.wallet.www.project.models.binding;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class EventsBindingModel {
    @NotNull
    @Size(min = 2,message = "Event name should be at least 2 symbols")
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getName() {
        return name;
    }

    public EventsBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public EventsBindingModel setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EventsBindingModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
