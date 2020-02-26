package com.siemens.dl.supply.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Alarm.
 */
@Entity
@Table(name = "alarm")
public class Alarm implements Serializable {

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

    @Column(name = "level")
    private String level;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "resolve_time")
    private ZonedDateTime resolveTime;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JsonIgnoreProperties("alarms")
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

    public Alarm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getPhenomenonTime() {
        return phenomenonTime;
    }

    public Alarm phenomenonTime(ZonedDateTime phenomenonTime) {
        this.phenomenonTime = phenomenonTime;
        return this;
    }

    public void setPhenomenonTime(ZonedDateTime phenomenonTime) {
        this.phenomenonTime = phenomenonTime;
    }

    public String getResult() {
        return result;
    }

    public Alarm result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ZonedDateTime getResultTime() {
        return resultTime;
    }

    public Alarm resultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
        return this;
    }

    public void setResultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public String getDescription() {
        return description;
    }

    public Alarm description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public Alarm level(String level) {
        this.level = level;
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public Alarm status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getResolveTime() {
        return resolveTime;
    }

    public Alarm resolveTime(ZonedDateTime resolveTime) {
        this.resolveTime = resolveTime;
        return this;
    }

    public void setResolveTime(ZonedDateTime resolveTime) {
        this.resolveTime = resolveTime;
    }

    public String getDetails() {
        return details;
    }

    public Alarm details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Factory getFactory() {
        return factory;
    }

    public Alarm factory(Factory factory) {
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
        if (!(o instanceof Alarm)) {
            return false;
        }
        return id != null && id.equals(((Alarm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Alarm{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phenomenonTime='" + getPhenomenonTime() + "'" +
            ", result='" + getResult() + "'" +
            ", resultTime='" + getResultTime() + "'" +
            ", description='" + getDescription() + "'" +
            ", level='" + getLevel() + "'" +
            ", status='" + getStatus() + "'" +
            ", resolveTime='" + getResolveTime() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
