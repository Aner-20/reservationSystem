package com.example.reservationService.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservationService.dto.CreateReservationRequest;
import com.example.reservationService.dto.ReservationResponse;
import com.example.reservationService.dto.UpdateReservationStatusRequest;
import com.example.reservationService.entity.Reservation;
import com.example.reservationService.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor 
public class ReservationController {
    
    private final ReservationService reservationService;
    
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody CreateReservationRequest request){
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable UUID id) {

        ReservationResponse response = reservationService.getReservationById(id);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {

        List<ReservationResponse> reservations = reservationService.getAllReservations();

        return ResponseEntity.ok(reservations);
    }



    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByCustomerId(@PathVariable UUID customerId) {

        List<ReservationResponse> reservations = reservationService.getReservationsByCustomerId(customerId);

        return ResponseEntity.ok(reservations);
    }


    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByRestaurantId(@PathVariable UUID restaurantId) {

        List<ReservationResponse> reservations = reservationService.getReservationsByRestaurantId(restaurantId);

        return ResponseEntity.ok(reservations);
    }


    @GetMapping("/table/{tableId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByTableId(@PathVariable UUID tableId) {

        List<ReservationResponse> reservations = reservationService.getReservationsByTableId(tableId);

        return ResponseEntity.ok(reservations);
    }


     public ResponseEntity<ReservationResponse> updateReservationStatus(@PathVariable UUID id, @Valid @RequestBody UpdateReservationStatusRequest request) {

        ReservationResponse response = reservationService.updateReservationStatus(id, request);

        return ResponseEntity.ok(response);
    }


     // Cancel reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable UUID id) {

        ReservationResponse response = reservationService.cancelReservation(id);

        return ResponseEntity.ok(response);
    }

}
