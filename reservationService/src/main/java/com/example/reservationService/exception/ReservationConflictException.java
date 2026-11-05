package com.example.reservationService.exception;

public class ReservationConflictException extends RuntimeException{
    public ReservationConflictException(String message) {
        super(message);
    }
}
