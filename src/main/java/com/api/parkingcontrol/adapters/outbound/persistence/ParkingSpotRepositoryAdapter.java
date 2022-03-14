package com.api.parkingcontrol.adapters.outbound.persistence;

import com.api.parkingcontrol.adapters.outbound.persistence.entities.ParkingSpotEntity;
import com.api.parkingcontrol.application.domain.PageInfo;
import com.api.parkingcontrol.application.domain.ParkingSpot;
import com.api.parkingcontrol.application.ports.ParkingSpotRepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Primary
public class ParkingSpotRepositoryAdapter implements ParkingSpotRepositoryPort {

    private final SpringDataParkingSpotRepository parkingSpotRepository;

    public ParkingSpotRepositoryAdapter(SpringDataParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Autowired
    ModelMapper mapper;

    @Override
    public ParkingSpot save(ParkingSpot parkingSpot) {
        var id = parkingSpot.getId();
        if (id == null)
            id = new UUID(0,0);

        if (parkingSpotRepository.existsByLicensePlateCarAndIdNotLike(parkingSpot.getLicensePlateCar(), id))
            throw new ValidationException("Conflict: License Plate Car is already in use!");
        if (parkingSpotRepository.existsByParkingSpotNumberAndIdNotLike(parkingSpot.getParkingSpotNumber(), id))
            throw new ValidationException("Conflict: Parking Spot is already in use!");
        if (parkingSpotRepository.existsByApartmentAndBlockAndIdNotLike(parkingSpot.getApartment(), parkingSpot.getBlock(), id))
            throw new ValidationException("Conflict: Parking Spot already registered for this apartment/block!");

        var parkingSpotEntity = mapper.map(parkingSpot, ParkingSpotEntity.class);
        parkingSpotEntity = parkingSpotRepository.save(parkingSpotEntity);
        return mapper.map(parkingSpotEntity, ParkingSpot.class);
    }

    @Override
    public List<ParkingSpot> findAll(PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return parkingSpotRepository.findAll(pageable).stream()
                .map(entity -> mapper.map(entity, ParkingSpot.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ParkingSpot> findById(UUID id) {
        var parkingSpotEntity = parkingSpotRepository.findById(id);
        if (parkingSpotEntity.isPresent())
            return Optional.of(mapper.map(parkingSpotEntity.get(), ParkingSpot.class));
        return Optional.empty();
    }

    @Override
    public void delete(ParkingSpot parkingSpot) {
        var parkingSpotEntity = mapper.map(parkingSpot, ParkingSpotEntity.class);
        parkingSpotRepository.delete(parkingSpotEntity);
    }
}
