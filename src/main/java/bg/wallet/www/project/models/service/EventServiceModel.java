package bg.wallet.www.project.models.service;

import java.time.LocalDate;

public class EventServiceModel {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getName() {
        return name;
    }

    public EventServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public EventServiceModel setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EventServiceModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
