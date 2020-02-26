package com.siemens.dl.supply.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.siemens.dl.supply.domain.Link} entity.
 */
public class LinkDTO implements Serializable {

    private Long id;

    private String name;

    @NotNull
    private String deviceId;

    private String description;


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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LinkDTO linkDTO = (LinkDTO) o;
        if (linkDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), linkDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LinkDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
