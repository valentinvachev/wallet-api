package bg.wallet.www.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }
}
