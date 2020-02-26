package com.siemens.dl.supply.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Kpi.
 */
@Entity
@Table(name = "kpi")
public class Kpi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phenomenon_time")
    private ZonedDateTime phenomenonTime;

    @NotNull
    @Column(name = "result", nullable = false)
    private String result;

    @NotNull
    @Column(name = "result_time", nullable = false)
    private ZonedDateTime resultTime;

    @Column(name = "description")
    private String description;

    @Column(name = "interval_time")
    private Long intervalTime;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;

    @Column(name = "reserve")
    private String reserve;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("kpis")
    private Factory factory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("kpis")
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

    public Kpi name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getPhenomenonTime() {
        return phenomenonTime;
    }

    public Kpi phenomenonTime(ZonedDateTime phenomenonTime) {
        this.phenomenonTime = phenomenonTime;
        return this;
    }

    public void setPhenomenonTime(ZonedDateTime phenomenonTime) {
        this.phenomenonTime = phenomenonTime;
    }

    public String getResult() {
        return result;
    }

    public Kpi result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ZonedDateTime getResultTime() {
        return resultTime;
    }

    public Kpi resultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
        return this;
    }

    public void setResultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public String getDescription() {
        return description;
    }

    public Kpi description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIntervalTime() {
        return intervalTime;
    }

    public Kpi intervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
        return this;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getType() {
        return type;
    }

    public Kpi type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public Kpi unitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
        return this;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getReserve() {
        return reserve;
    }

    public Kpi reserve(String reserve) {
        this.reserve = reserve;
        return this;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public Factory getFactory() {
        return factory;
    }

    public Kpi factory(Factory factory) {
        this.factory = factory;
        return this;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public Product getProduct() {
        return product;
    }

    public Kpi product(Product product) {
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
        if (!(o instanceof Kpi)) {
            return false;
        }
        return id != null && id.equals(((Kpi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Kpi{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phenomenonTime='" + getPhenomenonTime() + "'" +
            ", result='" + getResult() + "'" +
            ", resultTime='" + getResultTime() + "'" +
            ", description='" + getDescription() + "'" +
            ", intervalTime=" + getIntervalTime() +
            ", type='" + getType() + "'" +
            ", unitOfMeasurement='" + getUnitOfMeasurement() + "'" +
            ", reserve='" + getReserve() + "'" +
            "}";
    }
}
