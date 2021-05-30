package team14.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team14.airbnb.domain.aggregate.reservation.Reservation;
import team14.airbnb.domain.aggregate.user.User;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    List<Reservation> findAllByUserOrderByCreatedTimeDesc(User user);
}
