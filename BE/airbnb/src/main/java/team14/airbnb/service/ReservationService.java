package team14.airbnb.service;

import org.springframework.stereotype.Service;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.response.reservation.BookedAccommodationDto;
import team14.airbnb.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean canMakeReservation(long accommodationId, LocalDate startDate, LocalDate endDate) {
        return reservationRepository.canMakeReservation(accommodationId, startDate, endDate);
    }

    public List<BookedAccommodationDto> getBookedAccommodations(User user) {
        Set<Long> wishSet = user.getWishIdSet();

        return reservationRepository.findAllByUserOrderByCreatedTimeDesc(user).stream()
                .map(reservation -> BookedAccommodationDto.of(reservation, wishSet.contains(reservation.getAccommodationId())))
                .collect(Collectors.toList());
    }
}
