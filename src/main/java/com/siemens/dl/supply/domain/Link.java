package com.siemens.dl.supply.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Link.
 */
@Entity
@Table(name = "link")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "link")
    private Set<Observation> observations = new HashSet<>();

    @OneToOne(mappedBy = "link")
    @JsonIgnore
    private AssemblyLine assemblyline;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Link name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Link deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDescription() {
        return description;
    }

    public Link description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Observation> getObservations() {
        return observations;
    }

    public Link observations(Set<Observation> observations) {
        this.observations = observations;
        return this;
    }

    public Link addObservation(Observation observation) {
        this.observations.add(observation);
        observation.setLink(this);
        return this;
    }

    public Link removeObservation(Observation observation) {
        this.observations.remove(observation);
        observation.setLink(null);
        return this;
    }

    public void setObservations(Set<Observation> observations) {
        this.observations = observations;
    }

    public AssemblyLine getAssemblyline() {
        return assemblyline;
    }

    public Link assemblyline(AssemblyLine assemblyLine) {
        this.assemblyline = assemblyLine;
        return this;
    }

    public void setAssemblyline(AssemblyLine assemblyLine) {
        this.assemblyline = assemblyLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Link)) {
            return false;
        }
        return id != null && id.equals(((Link) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Link{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
