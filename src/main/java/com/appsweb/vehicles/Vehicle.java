package com.appsweb.vehicles;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
@Schema(
        description = "Represents a vehicle record in the system",
        name = "Vehicle"
)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Unique identifier of the vehicle",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Brand/manufacturer of the vehicle",
            example = "Toyota",
            minLength = 1,
            maxLength = 100
    )
    private String brand;

    @Schema(
            description = "Model name of the vehicle",
            example = "Corolla",
            minLength = 1,
            maxLength = 100
    )
    private String model;

    @Schema(
            description = "Availability status of the vehicle. True = available, False = unavailable",
            example = "true"
    )
    private Boolean status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}