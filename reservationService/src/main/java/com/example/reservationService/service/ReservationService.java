package com.example.reservationService.service;

import com.example.reservationService.dto.CreateReservationRequest;
import com.example.reservationService.dto.ReservationResponse;
import com.example.reservationService.dto.UpdateReservationStatusRequest;


import java.util.List;
import java.util.UUID;

public interface ReservationService {
    

    
    ReservationResponse createReservation(CreateReservationRequest request);

    ReservationResponse getReservationById(UUID id);

    List<ReservationResponse> getAllReservations();

    List<ReservationResponse> getReservationsByCustomerId(UUID customerId);

    List<ReservationResponse> getReservationsByRestaurantId(UUID restaurantId);

    List<ReservationResponse> getReservationsByTableId(UUID tableId);

    ReservationResponse updateReservationStatus(UUID id, UpdateReservationStatusRequest request);

    ReservationResponse cancelReservation(UUID id);

}
