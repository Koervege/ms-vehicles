package com.appsweb.vehicles;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    // Spring automatically generates the SQL based on these method names
    List<Vehicle> findByBrand(String brand);
    List<Vehicle> findByModel(String model);
    List<Vehicle> findByStatus(Boolean status);
    Optional<Vehicle> findById(@NonNull Long id);
}
