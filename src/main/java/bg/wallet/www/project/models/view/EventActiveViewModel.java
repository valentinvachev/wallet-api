package bg.wallet.www.project.models.view;
import java.time.LocalDate;

public class EventActiveViewModel {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;

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

    public String getStartDate() {
        return startDate;
    }

    public EventActiveViewModel setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public EventActiveViewModel setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }
}
