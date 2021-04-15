package com.applause.model;

import com.applause.config.jsonViews.BugView;
import com.applause.config.jsonViews.DeviceView;
import com.applause.config.jsonViews.SearchView;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Set;

import static org.hibernate.annotations.FetchMode.SELECT;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device")
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")*/
/*@JsonIdentityReference(alwaysAsId = true)*/
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({DeviceView.class, BugView.class, SearchView.class})
    private Long id;

    @JsonView({DeviceView.class, BugView.class, SearchView.class})
    private String description;


    @OneToMany(mappedBy = "device",  fetch = FetchType.LAZY)
    private Set<Bug> bugs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tester_device",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "tester_id"))
    Set<Tester> testers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(Set<Bug> bugs) {
        this.bugs = bugs;
    }

    public Set<Tester> getTesters() {
        return testers;
    }

    public void setTesters(Set<Tester> testers) {
        this.testers = testers;
    }
}
