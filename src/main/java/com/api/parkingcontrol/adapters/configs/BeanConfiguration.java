package com.api.parkingcontrol.adapters.configs;

import com.api.parkingcontrol.ParkingControlApplication;
import com.api.parkingcontrol.application.ports.ParkingSpotRepositoryPort;
import com.api.parkingcontrol.application.services.ParkingSpotServiceAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ParkingControlApplication.class)
public class BeanConfiguration {

    @Bean
    ParkingSpotServiceAdapter parkingSpotServiceImpl(ParkingSpotRepositoryPort parkingSpotRepositoryPort) {
        return new ParkingSpotServiceAdapter(parkingSpotRepositoryPort);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
