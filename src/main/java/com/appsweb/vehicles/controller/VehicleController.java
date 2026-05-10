package com.appsweb.vehicles.controller;

import com.appsweb.vehicles.model.Vehicle;
import com.appsweb.vehicles.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicle", description = "Vehicle management API")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @Operation(
            summary = "Create a new vehicle",
            description = "Creates a new vehicle record in the system. Returns the created vehicle with its generated ID and a Location header pointing to the new resource."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Vehicle created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data provided",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        Vehicle saved = service.saveVehicle(vehicle);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(
            summary = "Get all vehicles",
            description = "Returns a list of all vehicle records in the system. Returns 204 if no vehicles exist."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of vehicles retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Vehicle.class)))
            ),
            @ApiResponse(responseCode = "204", description = "No vehicles found",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll() {
        List<Vehicle> vehicles = service.getAllVehicles();
        return vehicles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicles);
    }

    @Operation(
            summary = "Get vehicles by brand",
            description = "Returns all vehicles matching the given brand name. The match is case-sensitive. Returns 204 if no vehicles are found for that brand."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicles found for the given brand",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Vehicle.class)))
            ),
            @ApiResponse(responseCode = "204", description = "No vehicles found for the given brand",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid brand parameter",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Vehicle>> getByBrand(
            @Parameter(description = "Brand/manufacturer to filter by", example = "Toyota")
            @PathVariable String brand) {
        List<Vehicle> vehicles = service.findByBrand(brand);
        return vehicles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicles);
    }

    @Operation(
            summary = "Get a vehicle by ID",
            description = "Returns a single vehicle matching the given ID. Returns 404 if no vehicle exists with that ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicle found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))
            ),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given ID",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid ID format",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Vehicle> getById(
            @Parameter(description = "Unique identifier of the vehicle", example = "1")
            @PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get vehicles by model",
            description = "Returns all vehicles matching the given model name. The match is case-sensitive. Returns 204 if no vehicles are found for that model."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicles found for the given model",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Vehicle.class)))
            ),
            @ApiResponse(responseCode = "204", description = "No vehicles found for the given model",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid model parameter",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/model/{model}")
    public ResponseEntity<List<Vehicle>> getByModel(
            @Parameter(description = "Model name to filter by", example = "Corolla")
            @PathVariable String model) {
        List<Vehicle> vehicles = service.findByModel(model);
        return vehicles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicles);
    }

    @Operation(
            summary = "Get vehicles by status",
            description = "Returns all vehicles matching the given availability status. Returns 204 if no vehicles are found for that status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicles found for the given status",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Vehicle.class)))
            ),
            @ApiResponse(responseCode = "204", description = "No vehicles found for the given status",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid status parameter",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Vehicle>> getByStatus(
            @Parameter(description = "Availability status to filter by. true = available, false = unavailable", example = "true")
            @PathVariable Boolean status) {
        List<Vehicle> vehicles = service.findByStatus(status);
        return vehicles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicles);
    }

    @Operation(
            summary = "Update a vehicle by ID",
            description = "Replaces all fields of an existing vehicle with the provided data. Returns 404 if no vehicle exists with that ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicle updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))
            ),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given ID",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data provided",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/id/{id}")
    public ResponseEntity<Vehicle> update(
            @Parameter(description = "Unique identifier of the vehicle to update", example = "1")
            @PathVariable Long id,
            @RequestBody Vehicle vehicle) {
        return service.updateVehicle(id, vehicle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a vehicle by ID",
            description = "Permanently deletes the vehicle with the given ID. Returns 404 if no vehicle exists with that ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given ID",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Unique identifier of the vehicle to delete", example = "1")
            @PathVariable Long id) {
        return service.deleteVehicle(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Update the status of a vehicle by ID",
            description = "Updates only the availability status of the vehicle with the given ID, leaving all other fields unchanged. Returns 404 if no vehicle exists with that ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicle status updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))
            ),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given ID",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid status value",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/id/{id}/status/{status}")
    public ResponseEntity<Vehicle> updateStatus(
            @Parameter(description = "Unique identifier of the vehicle", example = "1")
            @PathVariable Long id,
            @Parameter(description = "New availability status. true = available, false = unavailable", example = "false")
            @PathVariable Boolean status) {
        return service.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
