package com.example.reservationService.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReservationRequest {
    @NotNull
    private UUID customerId;

    @NotNull
    private UUID restaurantId;

    @NotNull
    private UUID tableId;

    @NotNull
    @Future
    private OffsetDateTime startTime;

    @NotNull
    private OffsetDateTime endTime;
    

}
