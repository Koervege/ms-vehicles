package com.appsweb.vehicles.controller;

import com.appsweb.vehicles.service.VehicleService;
import com.appsweb.vehicles.model.Vehicle;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create a new vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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

    @Operation(summary = "Get all vehicles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of vehicles retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No vehicles found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll() {
        List<Vehicle> vehicles = service.getAllVehicles();
        return vehicles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicles by brand")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicles found for the given brand"),
            @ApiResponse(responseCode = "204", description = "No vehicles found for the given brand"),
            @ApiResponse(responseCode = "400", description = "Invalid brand parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Vehicle>> getByBrand(@PathVariable String brand) {
        List<Vehicle> vehicles = service.findByBrand(brand);
        return vehicles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get a vehicle by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicle found"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given ID"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get vehicles by model")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicles found for the given model"),
            @ApiResponse(responseCode = "204", description = "No vehicles found for the given model"),
            @ApiResponse(responseCode = "400", description = "Invalid model parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/model/{model}")
    public ResponseEntity<List<Vehicle>> getByModel(@PathVariable String model) {
        List<Vehicle> vehicles = service.findByModel(model);
        return vehicles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicles by status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicles found for the given status"),
            @ApiResponse(responseCode = "204", description = "No vehicles found for the given status"),
            @ApiResponse(responseCode = "400", description = "Invalid status parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Vehicle>> getByStatus(@PathVariable Boolean status) {
        List<Vehicle> vehicles = service.findByStatus(status);
        return vehicles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Update a vehicle by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given ID"),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/id/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        return service.updateVehicle(id, vehicle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a vehicle by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteVehicle(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update the status of a vehicle by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicle status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found for the given ID"),
            @ApiResponse(responseCode = "400", description = "Invalid status value"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/id/{id}/status")
    public ResponseEntity<Vehicle> updateStatus(@PathVariable Long id, @RequestParam Boolean status) {
        return service.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
