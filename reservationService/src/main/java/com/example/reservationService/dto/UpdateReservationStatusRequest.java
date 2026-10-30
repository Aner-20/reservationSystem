package com.example.reservationService.dto;

import com.example.reservationService.entity.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.*;

@Setter 
@Getter
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class UpdateReservationStatusRequest {
    @NotNull
    private ReservationStatus status;
}
