package com.siemens.dl.supply.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.siemens.dl.supply.domain.AssemblyLine} entity.
 */
public class AssemblyLineDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String type;

    private String description;

    @NotNull
    private Instant capacity;

    @NotNull
    private Instant planCapacity;

    private String reserve;


    private Long linkId;

    private String linkName;

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

        AssemblyLineDTO assemblyLineDTO = (AssemblyLineDTO) o;
        if (assemblyLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assemblyLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssemblyLineDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", capacity='" + getCapacity() + "'" +
            ", planCapacity='" + getPlanCapacity() + "'" +
            ", reserve='" + getReserve() + "'" +
            ", linkId=" + getLinkId() +
            ", linkName='" + getLinkName() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}
