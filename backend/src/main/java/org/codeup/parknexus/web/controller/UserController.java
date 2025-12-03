package org.codeup.parknexus.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.codeup.parknexus.domain.ParkingSession;
import org.codeup.parknexus.domain.ParkingSpot;
import org.codeup.parknexus.domain.Reservation;
import org.codeup.parknexus.domain.enums.PaymentMethod;
import org.codeup.parknexus.domain.enums.SpotStatus;
import org.codeup.parknexus.domain.enums.SpotType;
import org.codeup.parknexus.exception.ResourceNotFoundException;
import org.codeup.parknexus.service.IReservationService;
import org.codeup.parknexus.service.IParkingService;
import org.codeup.parknexus.service.IPaymentService;
import org.codeup.parknexus.service.IUserService;
import org.codeup.parknexus.service.IAdminService;
import org.codeup.parknexus.web.dto.user.*;
import org.codeup.parknexus.web.mapper.ParkingSpotMapper;
import org.codeup.parknexus.web.mapper.ReservationMapper;
import org.codeup.parknexus.web.mapper.ParkingSessionMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IParkingService parkingService;
    private final IReservationService reservationService;
    private final IPaymentService paymentService;
    private final IAdminService adminService;
    private final ParkingSpotMapper parkingSpotMapper;
    private final ReservationMapper reservationMapper;
    private final ParkingSessionMapper parkingSessionMapper;

    @GetMapping("/user/dashboard")
    public ResponseEntity<DashboardResponse> dashboard(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(userService.getDashboard(userId));
    }

    @GetMapping("/buildings")
    public ResponseEntity<List<org.codeup.parknexus.web.dto.admin.BuildingResponse>> getBuildings() {
        // Public endpoint to get all buildings for filtering
        return ResponseEntity.ok(adminService.getAllBuildings());
    }

    @GetMapping("/spots/available")
    public ResponseEntity<List<ParkingSpotResponse>> availableSpots(
            @RequestParam(required = false) UUID buildingId,
            @RequestParam(required = false) UUID floorId,
            @RequestParam(required = false) SpotType type,
            @RequestParam(required = false, defaultValue = "AVAILABLE") SpotStatus status) {
        List<ParkingSpot> spots = parkingService.getAvailableSpots(buildingId, floorId, type, status);
        return ResponseEntity.ok(parkingSpotMapper.toUserResponses(spots));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> reserve(@Valid @RequestBody ReservationRequest request, @AuthenticationPrincipal UUID userId) {
        Reservation reservation = reservationService.createReservation(
                userId,
                request.getSpotId(),
                request.getStartTime(),
                request.getDurationMinutes()
        );
        return ResponseEntity.ok(reservationMapper.toResponse(reservation));
    }

    @PostMapping("/parking/check-in")
    public ResponseEntity<CheckInResponse> checkIn(@Valid @RequestBody CheckInRequest request, @AuthenticationPrincipal UUID userId) {
        var session = parkingService.checkIn(userId, request.getSpotId(), request.getVehicleNumber());
        CheckInResponse resp = CheckInResponse.builder()
                .sessionId(session.getId())
                .spotId(session.getSpot().getId())
                .spotNumber(session.getSpot().getSpotNumber())
                .vehicleNumber(session.getVehicleNumber())
                .buildingName(session.getSpot().getFloor().getBuilding().getName())
                .floorName("Floor " + session.getSpot().getFloor().getFloorNumber())
                .checkInTime(session.getCheckInTime())
                .status(session.getStatus().name())
                .message("Checked in successfully")
                .build();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/parking/calculate-fee")
    public ResponseEntity<FeeCalculationResponse> calculateFee(@RequestParam UUID sessionId) {
        try {
            FeeCalculationResponse response = parkingService.calculateFee(sessionId);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/parking/check-out")
    public ResponseEntity<CheckOutResponse> checkOut(
            @RequestParam UUID sessionId,
            @RequestParam(required = false, defaultValue = "CREDIT_CARD") PaymentMethod paymentMethod) {
        try {
            CheckOutResponse response = parkingService.checkOut(sessionId, paymentMethod);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/parking/sessions/active")
    public ResponseEntity<List<ParkingSessionResponse>> getActiveSessions(@AuthenticationPrincipal UUID userId) {
        List<ParkingSession> sessions = parkingService.getActiveSessions(userId);
        return ResponseEntity.ok(parkingSessionMapper.toResponses(sessions));
    }

    @GetMapping("/parking/sessions/my")
    public ResponseEntity<List<ParkingSessionResponse>> getMySessions(@AuthenticationPrincipal UUID userId) {
        List<ParkingSession> sessions = parkingService.getUserSessions(userId);
        return ResponseEntity.ok(parkingSessionMapper.toResponses(sessions));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations(@AuthenticationPrincipal UUID userId) {
        List<Reservation> reservations = reservationService.getUserReservations(userId);
        return ResponseEntity.ok(reservations.stream()
                .map(reservationMapper::toResponse)
                .toList());
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable UUID reservationId,
            @AuthenticationPrincipal UUID userId) {
        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/parking/payment/simulate")
    public ResponseEntity<PaymentResponse> simulatePayment(
            @RequestParam UUID sessionId,
            @RequestParam BigDecimal amount) {
        try {
            PaymentResponse response = paymentService.processPayment(sessionId, amount, PaymentMethod.CREDIT_CARD);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
