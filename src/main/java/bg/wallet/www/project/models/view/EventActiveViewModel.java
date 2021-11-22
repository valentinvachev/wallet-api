package bg.wallet.www.project.models.view;
import java.time.LocalDate;

public class EventActiveViewModel {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public EventActiveViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventActiveViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public EventActiveViewModel setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EventActiveViewModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
