package com.siemens.dl.supply.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.siemens.dl.supply.domain.Observation} entity.
 */
public class ObservationDTO implements Serializable {

    private Long id;

    private ZonedDateTime phenomenonTime;

    @NotNull
    private String result;

    @NotNull
    private ZonedDateTime resultTime;

    @NotNull
    private String property;

    private String unitOfMeasurement;

    private Long intervalTime;

    private String reserve;


    private Long assemblylineId;

    private String assemblylineName;

    private Long linkId;

    private String linkName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
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

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObservationDTO observationDTO = (ObservationDTO) o;
        if (observationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), observationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObservationDTO{" +
            "id=" + getId() +
            ", phenomenonTime='" + getPhenomenonTime() + "'" +
            ", result='" + getResult() + "'" +
            ", resultTime='" + getResultTime() + "'" +
            ", property='" + getProperty() + "'" +
            ", unitOfMeasurement='" + getUnitOfMeasurement() + "'" +
            ", intervalTime=" + getIntervalTime() +
            ", reserve='" + getReserve() + "'" +
            ", assemblylineId=" + getAssemblylineId() +
            ", assemblylineName='" + getAssemblylineName() + "'" +
            ", linkId=" + getLinkId() +
            ", linkName='" + getLinkName() + "'" +
            "}";
    }
}
