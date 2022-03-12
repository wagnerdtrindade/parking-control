package com.api.parkingcontrol.adapters.outbound.persistence;

import com.api.parkingcontrol.adapters.outbound.persistence.entities.ParkingSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataParkingSpotRepository extends JpaRepository<ParkingSpotEntity, UUID> {

    boolean existsByLicensePlateCar(String licensePlateCar);
    boolean existsByParkingSpotNumber(String parkingSpotNumber);
    boolean existsByApartmentAndBlock(String apartment, String block);
}
