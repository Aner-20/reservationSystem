package com.example.reservationService.mapper;

import org.mapstruct.*;

import com.example.reservationService.dto.CreateReservationRequest;
import com.example.reservationService.dto.ReservationResponse;
import com.example.reservationService.entity.Reservation;


@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Reservation toEntity(CreateReservationRequest request);

    ReservationResponse toResponse(Reservation reservation);
}
