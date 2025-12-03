package org.codeup.parknexus.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.codeup.parknexus.domain.ParkingSpot;
import org.codeup.parknexus.domain.Reservation;
import org.codeup.parknexus.domain.enums.SpotStatus;
import org.codeup.parknexus.domain.enums.SpotType;
import org.codeup.parknexus.service.IReservationService;
import org.codeup.parknexus.service.IParkingService;
import org.codeup.parknexus.service.IPaymentService;
import org.codeup.parknexus.service.IUserService;
import org.codeup.parknexus.web.dto.user.*;
import org.codeup.parknexus.web.mapper.ParkingSpotMapper;
import org.codeup.parknexus.web.mapper.ReservationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final ParkingSpotMapper parkingSpotMapper;
    private final ReservationMapper reservationMapper;

    @GetMapping("/user/dashboard")
    public ResponseEntity<DashboardResponse> dashboard(@RequestParam UUID userId) {
        return ResponseEntity.ok(userService.getDashboard(userId));
    }

    @GetMapping("/spots/available")
    public ResponseEntity<List<ParkingSpotResponse>> availableSpots(
            @RequestParam(required = false) UUID buildingId,
            @RequestParam(required = false) UUID floorId,
            @RequestParam(required = false) SpotType type,
            @RequestParam(required = false, defaultValue = "AVAILABLE") SpotStatus status) {
        List<ParkingSpot> spots = parkingService.getAvailableSpots(); // TODO: pass filters when service is updated
        return ResponseEntity.ok(parkingSpotMapper.toUserResponses(spots));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> reserve(@Valid @RequestBody ReservationRequest request, @RequestParam UUID userId) {
        Reservation reservation = reservationService.createReservation(
                org.codeup.parknexus.domain.User.builder().id(userId).build(),
                request.getSpotId(),
                request.getStartTime(),
                request.getDurationMinutes()
        );
        return ResponseEntity.ok(reservationMapper.toResponse(reservation));
    }

    @PostMapping("/parking/check-in")
    public ResponseEntity<CheckInResponse> checkIn(@Valid @RequestBody CheckInRequest request, @RequestParam UUID userId) {
        org.codeup.parknexus.domain.User user = org.codeup.parknexus.domain.User.builder().id(userId).build();
        var session = parkingService.checkIn(user, request.getSpotId());
        CheckInResponse resp = CheckInResponse.builder()
                .sessionId(session.getId())
                .spotId(session.getSpot().getId())
                .spotNumber(session.getSpot().getSpotNumber())
                .checkInTime(session.getCheckInTime())
                .message("Checked in successfully")
                .build();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/parking/check-out")
    public ResponseEntity<CheckOutResponse> checkOut(@RequestParam UUID sessionId) {
        return ResponseEntity.ok(parkingService.checkOut(sessionId));
    }

    @PostMapping("/payment/simulate")
    public ResponseEntity<PaymentResponse> simulatePayment(@Valid @RequestBody PaymentRequest request) {
        // Simulating a payment without persisting; controllers using real gateway would call service
        PaymentResponse resp = PaymentResponse.builder()
                .sessionId(request.getSessionId())
                .amount(request.getAmount())
                .status("PENDING")
                .transactionId("SIMULATED")
                .build();
        return ResponseEntity.ok(resp);
    }
}
