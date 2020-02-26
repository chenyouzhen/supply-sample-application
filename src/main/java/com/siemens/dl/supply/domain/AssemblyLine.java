package com.siemens.dl.supply.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A AssemblyLine.
 */
@Entity
@Table(name = "assembly_line")
public class AssemblyLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Instant capacity;

    @NotNull
    @Column(name = "plan_capacity", nullable = false)
    private Instant planCapacity;

    @Column(name = "reserve")
    private String reserve;

    @OneToOne
    @JoinColumn(unique = true)
    private Link link;

    @OneToMany(mappedBy = "assemblyline")
    private Set<Observation> observations = new HashSet<>();

    @OneToMany(mappedBy = "assemblyline")
    private Set<Record> records = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("assemblylines")
    private Product product;

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

    public AssemblyLine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public AssemblyLine type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public AssemblyLine description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCapacity() {
        return capacity;
    }

    public AssemblyLine capacity(Instant capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Instant capacity) {
        this.capacity = capacity;
    }

    public Instant getPlanCapacity() {
        return planCapacity;
    }

    public AssemblyLine planCapacity(Instant planCapacity) {
        this.planCapacity = planCapacity;
        return this;
    }

    public void setPlanCapacity(Instant planCapacity) {
        this.planCapacity = planCapacity;
    }

    public String getReserve() {
        return reserve;
    }

    public AssemblyLine reserve(String reserve) {
        this.reserve = reserve;
        return this;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public Link getLink() {
        return link;
    }

    public AssemblyLine link(Link link) {
        this.link = link;
        return this;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Set<Observation> getObservations() {
        return observations;
    }

    public AssemblyLine observations(Set<Observation> observations) {
        this.observations = observations;
        return this;
    }

    public AssemblyLine addObservation(Observation observation) {
        this.observations.add(observation);
        observation.setAssemblyline(this);
        return this;
    }

    public AssemblyLine removeObservation(Observation observation) {
        this.observations.remove(observation);
        observation.setAssemblyline(null);
        return this;
    }

    public void setObservations(Set<Observation> observations) {
        this.observations = observations;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public AssemblyLine records(Set<Record> records) {
        this.records = records;
        return this;
    }

    public AssemblyLine addRecord(Record record) {
        this.records.add(record);
        record.setAssemblyline(this);
        return this;
    }

    public AssemblyLine removeRecord(Record record) {
        this.records.remove(record);
        record.setAssemblyline(null);
        return this;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    public Product getProduct() {
        return product;
    }

    public AssemblyLine product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssemblyLine)) {
            return false;
        }
        return id != null && id.equals(((AssemblyLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssemblyLine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", capacity='" + getCapacity() + "'" +
            ", planCapacity='" + getPlanCapacity() + "'" +
            ", reserve='" + getReserve() + "'" +
            "}";
    }
}
