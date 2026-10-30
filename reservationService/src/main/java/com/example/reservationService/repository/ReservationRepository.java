package com.example.reservationService.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reservationService.entity.Reservation;
import com.example.reservationService.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByCustomerId(UUID customerId);

    List<Reservation> findByRestaurantId(UUID restaurantId);

    List<Reservation> findByTableId(UUID tableId);

    List<Reservation> findByStatus(ReservationStatus status);
}
