package com.siemens.dl.supply.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.siemens.dl.supply.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String type;

    private String description;

    private Instant capacity;

    private Instant planCapacity;

    private String reserve;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCapacity() {
        return capacity;
    }

    public void setCapacity(Instant capacity) {
        this.capacity = capacity;
    }

    public Instant getPlanCapacity() {
        return planCapacity;
    }

    public void setPlanCapacity(Instant planCapacity) {
        this.planCapacity = planCapacity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", capacity='" + getCapacity() + "'" +
            ", planCapacity='" + getPlanCapacity() + "'" +
            ", reserve='" + getReserve() + "'" +
            ", factoryId=" + getFactoryId() +
            ", factoryName='" + getFactoryName() + "'" +
            "}";
    }
}
