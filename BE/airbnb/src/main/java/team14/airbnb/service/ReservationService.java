package team14.airbnb.service;

import org.springframework.stereotype.Service;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.reservation.Reservation;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.response.reservation.BookedAccommodationDto;
import team14.airbnb.exception.DuplicatedReservationException;
import team14.airbnb.exception.NotFoundException;
import team14.airbnb.exception.UnauthorizedException;
import team14.airbnb.repository.AccommodationRepository;
import team14.airbnb.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;

    public ReservationService(ReservationRepository reservationRepository, AccommodationRepository accommodationRepository) {
        this.reservationRepository = reservationRepository;
        this.accommodationRepository = accommodationRepository;
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

    public void makeReservation(long accommodationId, LocalDate startDate, LocalDate endDate, User user) {
        if (!canMakeReservation(accommodationId, startDate, endDate)) {
            throw new DuplicatedReservationException();
        }

        Accommodation accommodation = accommodationRepository.findById(accommodationId).
                orElseThrow(() -> new NotFoundException(accommodationId + "에 해당하는 숙소가 없습니다."));
        accommodation.setStartEndDate(startDate, endDate);

        Reservation reservation = reservationRepository.save(new Reservation(startDate, endDate, accommodation.getTotalFee(), user, accommodation));

        if (reservationRepository.countReservation(accommodationId, startDate, endDate) != 1) {
            reservationRepository.delete(reservation);
            throw new DuplicatedReservationException();
        }
    }

    public void cancelReservation(long reservationId, User user) {
        Reservation reservation = reservationRepository.findById(reservationId).
                orElseThrow(() -> new NotFoundException(reservationId + "번 예약은 존재하지 않습니다."));

        if (reservation.getUserId() != user.getId()) {
            throw new UnauthorizedException(reservationId + "번 예약을 취소할 권한이 없습니다.");
        }

        reservation.setDeleted(true);
        reservationRepository.save(reservation);
    }
}
