package com.siemens.dl.supply.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.siemens.dl.supply.domain.Record} entity.
 */
public class RecordDTO implements Serializable {

    private Long id;

    private String name;

    private ZonedDateTime phenomenonTime;

    @NotNull
    private String result;

    @NotNull
    private ZonedDateTime resultTime;

    private String description;

    private Long intervalTime;

    @NotNull
    private String type;

    private String unitOfMeasurement;

    private String reserve;


    private Long assemblylineId;

    private String assemblylineName;

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

    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public Long getAssemblylineId() {
        return assemblylineId;
    }

    public void setAssemblylineId(Long assemblyLineId) {
        this.assemblylineId = assemblyLineId;
    }

    public String getAssemblylineName() {
        return assemblylineName;
    }

    public void setAssemblylineName(String assemblyLineName) {
        this.assemblylineName = assemblyLineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecordDTO recordDTO = (RecordDTO) o;
        if (recordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecordDTO{" +
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
            ", assemblylineId=" + getAssemblylineId() +
            ", assemblylineName='" + getAssemblylineName() + "'" +
            "}";
    }
}
