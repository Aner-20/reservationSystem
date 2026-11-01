package com.example.reservationService.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reservationService.dto.CreateReservationRequest;
import com.example.reservationService.dto.ReservationResponse;
import com.example.reservationService.dto.UpdateReservationStatusRequest;
import com.example.reservationService.entity.Reservation;
import com.example.reservationService.entity.ReservationStatus;
import com.example.reservationService.mapper.ReservationMapper;
import com.example.reservationService.repository.ReservationRepository;
import com.example.reservationService.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class ReservationServiceImpl implements ReservationService {
    
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    
    @Override
    public ReservationResponse createReservation(CreateReservationRequest request) {
        // Controlla che l'orario di fine della prenotazione sia successivo all'orario di inizio
        // isAfter() è un metodo di Java usato con oggetti temporali, come LocalDateTime e LocalTime 
        // true se il primo orario è successivo al secondo
        // false altrimenti
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        Reservation reservation = reservationMapper.toEntity(request);

        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(savedReservation);

    }

    @Override
    // @Transactional indica a un metodo che deve essere eseguito in una transazione di sola lettura
    // readOnly = true è solo una transazione di lettura 
    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(UUID id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));

        return reservationMapper.toResponse(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByCustomerId(UUID customerId) {
         return reservationRepository.findByCustomerId(customerId)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByRestaurantId(UUID restaurantId) {
         return reservationRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByTableId(UUID tableId) {
         return reservationRepository.findByTableId(tableId)
                .stream() // elabora collezioni di dati
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    public ReservationResponse updateReservationStatus(UUID id, UpdateReservationStatusRequest request) {
        
         Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Reservation not found with ID: " + id
                ));

        ReservationStatus newStatus = request.getStatus();

        validateStatusTransition(reservation.getStatus(), newStatus);

        reservation.setStatus(newStatus);

        Reservation updatedReservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(updatedReservation);

    }


    @Override
    public ReservationResponse cancelReservation(UUID id) {

         Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));

        
        validateStatusTransition(reservation.getStatus(), ReservationStatus.CANCELLED);

        reservation.setStatus(ReservationStatus.CANCELLED);

        Reservation cancelledReservation = reservationRepository.save(reservation);

        return reservationMapper.toResponse(cancelledReservation);
    
    
    }
    
    
    private void validateStatusTransition(ReservationStatus currentStatus, ReservationStatus newStatus){
         boolean validTransition = switch (currentStatus) {

            case PENDING ->
                    newStatus == ReservationStatus.CONFIRMED || // prenotazione confermata
                    newStatus == ReservationStatus.CANCELLED; // prenotazione annullata 

            case CONFIRMED ->
                    newStatus == ReservationStatus.CANCELLED || // prenotazione annullata
                    newStatus == ReservationStatus.COMPLETED; // prenotazione completata

            // Se la prenotazione è CANCELLED, non può più cambiare stato
            // Se la prenotazione è COMPLETED, non può più cambiare stato 

            case CANCELLED, COMPLETED -> false; 
        };


        if (!validTransition) {
            throw new IllegalStateException(
                    "Invalid reservation status transition: "
                            + currentStatus + " -> " + newStatus
            );
        }
    }


}
