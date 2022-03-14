package com.api.parkingcontrol.adapters.inbound.controllers;

import com.api.parkingcontrol.adapters.dtos.ParkingSpotDto;
import com.api.parkingcontrol.application.domain.PageInfo;
import com.api.parkingcontrol.application.domain.ParkingSpot;
import com.api.parkingcontrol.application.exceptions.NotFoundException;
import com.api.parkingcontrol.application.ports.ParkingSpotServicePort;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotServicePort parkingSpotServicePort;

    public ParkingSpotController(ParkingSpotServicePort parkingSpotServicePort) {
        this.parkingSpotServicePort = parkingSpotServicePort;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        var parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotServicePort.save(parkingSpot));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpot>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var pageInfo = new PageInfo();
        BeanUtils.copyProperties(pageable, pageInfo);
        var parkingSpotList = parkingSpotServicePort.findAll(pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<ParkingSpot>(parkingSpotList, pageable, parkingSpotList.size()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        try {
            var parkingSpot = parkingSpotServicePort.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(parkingSpot);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
        try {
            parkingSpotServicePort.delete(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        var parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
        parkingSpot.setId(id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServicePort.update(parkingSpot));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
