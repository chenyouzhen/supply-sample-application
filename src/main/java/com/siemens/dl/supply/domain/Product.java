package com.siemens.dl.supply.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

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

    @Column(name = "capacity")
    private Instant capacity;

    @Column(name = "plan_capacity")
    private Instant planCapacity;

    @Column(name = "reserve")
    private String reserve;

    @OneToMany(mappedBy = "product")
    private Set<AssemblyLine> assemblylines = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Kpi> kpis = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("products")
    private Factory factory;

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Product type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCapacity() {
        return capacity;
    }

    public Product capacity(Instant capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Instant capacity) {
        this.capacity = capacity;
    }

    public Instant getPlanCapacity() {
        return planCapacity;
    }

    public Product planCapacity(Instant planCapacity) {
        this.planCapacity = planCapacity;
        return this;
    }

    public void setPlanCapacity(Instant planCapacity) {
        this.planCapacity = planCapacity;
    }

    public String getReserve() {
        return reserve;
    }

    public Product reserve(String reserve) {
        this.reserve = reserve;
        return this;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public Set<AssemblyLine> getAssemblylines() {
        return assemblylines;
    }

    public Product assemblylines(Set<AssemblyLine> assemblyLines) {
        this.assemblylines = assemblyLines;
        return this;
    }

    public Product addAssemblyline(AssemblyLine assemblyLine) {
        this.assemblylines.add(assemblyLine);
        assemblyLine.setProduct(this);
        return this;
    }

    public Product removeAssemblyline(AssemblyLine assemblyLine) {
        this.assemblylines.remove(assemblyLine);
        assemblyLine.setProduct(null);
        return this;
    }

    public void setAssemblylines(Set<AssemblyLine> assemblyLines) {
        this.assemblylines = assemblyLines;
    }

    public Set<Kpi> getKpis() {
        return kpis;
    }

    public Product kpis(Set<Kpi> kpis) {
        this.kpis = kpis;
        return this;
    }

    public Product addKpi(Kpi kpi) {
        this.kpis.add(kpi);
        kpi.setProduct(this);
        return this;
    }

    public Product removeKpi(Kpi kpi) {
        this.kpis.remove(kpi);
        kpi.setProduct(null);
        return this;
    }

    public void setKpis(Set<Kpi> kpis) {
        this.kpis = kpis;
    }

    public Factory getFactory() {
        return factory;
    }

    public Product factory(Factory factory) {
        this.factory = factory;
        return this;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
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
