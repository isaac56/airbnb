package team14.airbnb.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.accommodation.QAccommodation;
import team14.airbnb.domain.aggregate.accommodation.QAccommodationAddress;
import team14.airbnb.domain.aggregate.reservation.QReservation;

import java.time.LocalDate;
import java.util.List;

public class AccommodationRepositoryCustomImpl implements AccommodationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public AccommodationRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Accommodation> findByRegionsCustom(String region1, String region2, String region3, LocalDate startDate, LocalDate endDate) {
        QAccommodation accommodation = QAccommodation.accommodation;
        QAccommodationAddress accommodationAddress = QAccommodationAddress.accommodationAddress;
        QReservation reservation = QReservation.reservation;

        BooleanBuilder regionCondition = new BooleanBuilder();
        if (region1 != null && !region1.isEmpty()) {
            regionCondition.and(accommodationAddress.region1DepthName.eq(region1));
        }
        if (region2 != null && !region2.isEmpty()) {
            regionCondition.and(accommodationAddress.region2DepthName.eq(region2));
        }
        if (region3 != null && !region3.isEmpty()) {
            regionCondition.and(accommodationAddress.region3DepthName.eq(region3));
        }

        BooleanBuilder reservationCondition = new BooleanBuilder();
        reservationCondition.and(accommodation.id.eq(reservation.accommodation.id))
                .and(
                        reservation.startDate.between(startDate, endDate).and(reservation.startDate.ne(endDate))
                                .or(reservation.endDate.between(startDate, endDate).and(reservation.endDate.ne(startDate)))
                                .or(reservation.startDate.before(startDate).and(reservation.endDate.after(endDate)))
                );

        return jpaQueryFactory.selectFrom(accommodation)
                .innerJoin(accommodationAddress)
                .on(regionCondition)
                .leftJoin(reservation)
                .on(reservationCondition)
                .where(reservation.id.isNull())
                .fetch();
    }
}
