package com.siemens.dl.supply.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.siemens.dl.supply.domain.Alarm} entity.
 */
public class AlarmDTO implements Serializable {

    private Long id;

    private String name;

    private ZonedDateTime phenomenonTime;

    @NotNull
    private String result;

    @NotNull
    private ZonedDateTime resultTime;

    private String description;

    private String level;

    @NotNull
    private String status;

    private ZonedDateTime resolveTime;

    private String details;


    private Long factoryId;

    private String factoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getPhenomenonTime() {
        return phenomenonTime;
    }

    public void setPhenomenonTime(ZonedDateTime phenomenonTime) {
        this.phenomenonTime = phenomenonTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ZonedDateTime getResultTime() {
        return resultTime;
    }

    public void setResultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(ZonedDateTime resolveTime) {
        this.resolveTime = resolveTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlarmDTO alarmDTO = (AlarmDTO) o;
        if (alarmDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alarmDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlarmDTO{" +
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
            ", factoryId=" + getFactoryId() +
            ", factoryName='" + getFactoryName() + "'" +
            "}";
    }
}
