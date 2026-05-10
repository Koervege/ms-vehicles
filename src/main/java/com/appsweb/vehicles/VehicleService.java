package com.appsweb.vehicles;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public List<Vehicle> findByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    public List<Vehicle> findByModel(String model) {
        return repository.findByModel(model);
    }

    public List<Vehicle> findByStatus(Boolean status) {
        return repository.findByStatus(status);
    }

    public  Optional<Vehicle> findById(@NonNull Long id) {
        return repository.findById(id);
    }

    public Optional<Vehicle> updateVehicle(Long id, Vehicle updated) {
        return repository.findById(id).map(existing -> {
            existing.setBrand(updated.getBrand());
            existing.setModel(updated.getModel());
            existing.setStatus(updated.getStatus());
            return repository.save(existing);
        });
    }

    public boolean deleteVehicle(Long id) {
        return repository.findById(id).map(existing -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }

    public Optional<Vehicle> updateStatus(Long id, Boolean status) {
        return repository.findById(id).map(existing -> {
            existing.setStatus(status);
            return repository.save(existing);
        });
    }
}
