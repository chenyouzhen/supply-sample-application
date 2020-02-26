package com.siemens.dl.supply.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.siemens.dl.supply.domain.Kpi} entity.
 */
public class KpiDTO implements Serializable {

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


    private Long factoryId;

    private String factoryName;

    private Long productId;

    private String productName;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KpiDTO kpiDTO = (KpiDTO) o;
        if (kpiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kpiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KpiDTO{" +
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
            ", factoryId=" + getFactoryId() +
            ", factoryName='" + getFactoryName() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}
