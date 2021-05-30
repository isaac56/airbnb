package team14.airbnb.service;

import org.springframework.stereotype.Service;
import team14.airbnb.repository.ReservationRepository;

import java.time.LocalDate;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean canMakeReservation(long accommodationId, LocalDate startDate, LocalDate endDate) {
        return reservationRepository.canMakeReservation(accommodationId, startDate, endDate);
    }
    

}
