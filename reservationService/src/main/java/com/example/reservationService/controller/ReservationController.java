package com.example.reservationService.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservationService.dto.CreateReservationRequest;
import com.example.reservationService.dto.ReservationResponse;
import com.example.reservationService.dto.UpdateReservationStatusRequest;
import com.example.reservationService.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Annotazioni per documentazione
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@Tag(name = "Prenotazioni", description = "Endpoints per la gestione delle prenotazioni di un ristorante")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor 
public class ReservationController {
    
    private final ReservationService reservationService;

    @PostMapping
    @Operation(
        summary = "Crea una prenotazione",
        description = "Crea una nuova prenotazione del ristorante"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reservation created"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody CreateReservationRequest request){
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    @Operation(
        summary = "Get reservation by ID",
        description = "Retrieves a reservation using its UUID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservation found"),
        @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable UUID id) {

        ReservationResponse response = reservationService.getReservationById(id);

        return ResponseEntity.ok(response);
    }




    @GetMapping
    @Operation(
        summary = "Get all reservations",
        description = "Retrieves all reservations stored in the system"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully")
    })
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {

        List<ReservationResponse> reservations = reservationService.getAllReservations();

        return ResponseEntity.ok(reservations);
    }



    @GetMapping("/customer/{customerId}")
    @Operation(
        summary = "Get reservations by customer ID",
        description = "Retrieves all reservations associated with a specific customer"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<List<ReservationResponse>> getReservationsByCustomerId(@PathVariable UUID customerId) {

        List<ReservationResponse> reservations = reservationService.getReservationsByCustomerId(customerId);

        return ResponseEntity.ok(reservations);
    }


    @GetMapping("/restaurant/{restaurantId}")
    @Operation(
        summary = "Get reservations by restaurant ID",
        description = "Retrieves all reservations associated with a specific restaurant"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<List<ReservationResponse>> getReservationsByRestaurantId(@PathVariable UUID restaurantId) {

        List<ReservationResponse> reservations = reservationService.getReservationsByRestaurantId(restaurantId);

        return ResponseEntity.ok(reservations);
    }


    @GetMapping("/table/{tableId}")
    @Operation(
        summary = "Get reservations by table ID",
        description = "Retrieves all reservations associated with a specific restaurant table"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Table not found")
    })
    public ResponseEntity<List<ReservationResponse>> getReservationsByTableId(@PathVariable UUID tableId) {

        List<ReservationResponse> reservations = reservationService.getReservationsByTableId(tableId);

        return ResponseEntity.ok(reservations);
    }

    @PatchMapping("/{id}/status")
    @Operation(
        summary = "Update reservation status",
        description = "Updates the status of an existing reservation"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservation status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid status or invalid status transition"),
        @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResponse> updateReservationStatus(@PathVariable UUID id, @Valid @RequestBody UpdateReservationStatusRequest request) {

        ReservationResponse response = reservationService.updateReservationStatus(id, request);

        return ResponseEntity.ok(response);
    }


     // Cancel reservation
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Cancel reservation",
        description = "Cancels an existing reservation by updating its status to CANCELLED"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Reservation cancelled successfully"),
        @ApiResponse(responseCode = "404", description = "Reservation not found"),
        @ApiResponse(responseCode = "400", description = "Reservation cannot be cancelled")
    })
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable UUID id) {

        ReservationResponse response = reservationService.cancelReservation(id);

        return ResponseEntity.ok(response);
    }

}
