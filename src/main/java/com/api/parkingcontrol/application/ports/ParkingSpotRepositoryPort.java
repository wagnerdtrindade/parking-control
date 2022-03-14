package com.api.parkingcontrol.application.ports;

import com.api.parkingcontrol.application.domain.PageInfo;
import com.api.parkingcontrol.application.domain.ParkingSpot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingSpotRepositoryPort {

    ParkingSpot save(ParkingSpot parkingSpot);

    List<ParkingSpot> findAll(PageInfo pageInfo);

    Optional<ParkingSpot> findById(UUID id);

    void delete(ParkingSpot parkingSpotModel);
}

