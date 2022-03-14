package com.api.parkingcontrol.adapters.outbound.persistence;

import com.api.parkingcontrol.adapters.outbound.persistence.entities.ParkingSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataParkingSpotRepository extends JpaRepository<ParkingSpotEntity, UUID> {

    boolean existsByLicensePlateCarAndIdNotLike(String licensePlateCar, UUID id);
    boolean existsByParkingSpotNumberAndIdNotLike(String parkingSpotNumber, UUID id);
    boolean existsByApartmentAndBlockAndIdNotLike(String apartment, String block, UUID id);
}
