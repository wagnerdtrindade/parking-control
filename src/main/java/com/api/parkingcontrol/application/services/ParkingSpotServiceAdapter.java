package com.api.parkingcontrol.application.services;

import com.api.parkingcontrol.application.domain.PageInfo;
import com.api.parkingcontrol.application.domain.ParkingSpot;
import com.api.parkingcontrol.application.exceptions.NotFoundException;
import com.api.parkingcontrol.application.ports.ParkingSpotRepositoryPort;
import com.api.parkingcontrol.application.ports.ParkingSpotServicePort;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public class ParkingSpotServiceAdapter implements ParkingSpotServicePort {

    private final ParkingSpotRepositoryPort parkingSpotRepositoryPort;

    public ParkingSpotServiceAdapter(ParkingSpotRepositoryPort parkingSpotRepository) {
        this.parkingSpotRepositoryPort = parkingSpotRepository;
    }

    @Override
    @Transactional
    public ParkingSpot save(ParkingSpot parkingSpot) {
        parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return parkingSpotRepositoryPort.save(parkingSpot);
    }

    @Override
    public ParkingSpot findById(UUID id) throws NotFoundException {
        var parkingSpotOptional = parkingSpotRepositoryPort.findById(id);
        if (!parkingSpotOptional.isPresent())
            throw new NotFoundException();
        return parkingSpotOptional.get();
    }

    @Override
    public ParkingSpot update(ParkingSpot parkingSpot) throws NotFoundException {
        var original= findById(parkingSpot.getId());
        parkingSpot.setRegistrationDate(original.getRegistrationDate());
        return parkingSpotRepositoryPort.save(parkingSpot);
    }

    @Override
    public List<ParkingSpot> findAll(PageInfo pageInfo) {
        return parkingSpotRepositoryPort.findAll(pageInfo);
    }

    @Override
    @Transactional
    public void delete(UUID id) throws NotFoundException {
        var parkingSpot = findById(id);
        parkingSpotRepositoryPort.delete(parkingSpot);
    }
}
