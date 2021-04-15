package com.applause.model;

import com.applause.config.jsonViews.BugView;
import com.applause.config.jsonViews.TesterView;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;




@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bug")
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIdentityReference(alwaysAsId = true)*/
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({TesterView.class, BugView.class})
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name="device_id")
    @JsonView(BugView.class)
    private Device device;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tester_id")
    @JsonView(BugView.class)
    private Tester tester;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }
}
