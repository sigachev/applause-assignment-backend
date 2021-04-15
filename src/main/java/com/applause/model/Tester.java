package com.applause.model;

import com.applause.config.jsonViews.BugView;
import com.applause.config.jsonViews.SearchView;
import com.applause.config.jsonViews.TesterView;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static org.hibernate.annotations.FetchMode.SELECT;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tester")
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")*/
/*@JsonIdentityReference(alwaysAsId = true)*/
public class Tester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({TesterView.class, SearchView.class})
    private Long id;
    @JsonView({TesterView.class, BugView.class, SearchView.class})
    private String firstName;
    @JsonView({TesterView.class, BugView.class, SearchView.class})
    private String lastName;
    @JsonView({TesterView.class, BugView.class, SearchView.class})
    private String country;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-dd-MM HH:mm:ss")
    @JsonView(TesterView.class)
    private LocalDateTime lastLogin;


    @OneToMany( mappedBy = "tester", fetch = FetchType.LAZY )
    Set<Bug> bugs;

    @ManyToMany(fetch = FetchType.LAZY )
    @JoinTable(
            name = "tester_device",
            joinColumns = @JoinColumn(name = "tester_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id"))

    Set<Device> devices;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(Set<Bug> bugs) {
        this.bugs = bugs;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
}
