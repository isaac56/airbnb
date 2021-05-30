package team14.airbnb.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import team14.airbnb.domain.aggregate.reservation.QReservation;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public ReservationRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    public boolean canMakeReservation(long accommodationId, LocalDate startDate, LocalDate endDate) {
        QReservation reservation = QReservation.reservation;

        Integer reservationExisting = jpaQueryFactory.selectOne()
                .from(reservation)
                .where(reservation.accommodation.id.eq(accommodationId).and(reservation.deleted.isFalse())
                        .and(
                                reservation.startDate.between(startDate, endDate).and(reservation.startDate.ne(endDate))
                                        .or(reservation.endDate.between(startDate, endDate).and(reservation.endDate.ne(startDate)))
                                        .or(reservation.startDate.before(startDate).and(reservation.endDate.after(endDate)))
                        )
                )
                .fetchFirst();

        return reservationExisting == null;
    }
}
