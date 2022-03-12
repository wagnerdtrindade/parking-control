package com.api.parkingcontrol.application.ports;

import com.api.parkingcontrol.application.domain.PageInfo;
import com.api.parkingcontrol.application.domain.ParkingSpot;
import com.api.parkingcontrol.application.exceptions.NotFoundException;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

public interface ParkingSpotServicePort {

    @Transactional
    ParkingSpot save(ParkingSpot parkingSpot) throws ValidationException;

    @Transactional
    ParkingSpot update(ParkingSpot parkingSpot) throws NotFoundException;

    List<ParkingSpot> findAll(PageInfo pageInfo);

    ParkingSpot findById(UUID id) throws NotFoundException;

    @Transactional
    void delete(UUID id) throws NotFoundException;
}
