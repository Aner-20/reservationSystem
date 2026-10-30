package com.example.reservationService.dto;


import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.reservationService.entity.ReservationStatus;

import lombok.*;


@Setter 
@Getter
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class ReservationResponse {
    
    private UUID id;
    private UUID customerId;
    private UUID restaurantId;
    private UUID tableId;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private ReservationStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


}
