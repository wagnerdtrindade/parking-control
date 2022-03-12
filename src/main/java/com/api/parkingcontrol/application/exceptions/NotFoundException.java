package com.api.parkingcontrol.application.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super("Parking Spot not found.");
    }
}
