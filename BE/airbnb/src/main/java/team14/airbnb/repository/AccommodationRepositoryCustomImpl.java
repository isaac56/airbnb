package team14.airbnb.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.accommodation.QAccommodation;
import team14.airbnb.domain.aggregate.accommodation.QAccommodationAddress;
import team14.airbnb.domain.aggregate.accommodation.QDetailCondition;
import team14.airbnb.domain.aggregate.reservation.QReservation;
import team14.airbnb.utils.LocationUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.awt.geom.Rectangle2D;
import java.time.LocalDate;
import java.util.List;

public class AccommodationRepositoryCustomImpl implements AccommodationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Autowired
    public AccommodationRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    public List<Accommodation> findByRegionsCustom(String region1, String region2, String region3,
                                                   LocalDate startDate, LocalDate endDate, Integer numberOfPeople) {
        QAccommodation accommodation = QAccommodation.accommodation;
        QAccommodationAddress accommodationAddress = QAccommodationAddress.accommodationAddress;
        QReservation reservation = QReservation.reservation;
        QDetailCondition detailCondition = QDetailCondition.detailCondition;

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

        JPAQueryBase jpaQueryBase = jpaQueryFactory.selectFrom(accommodation)
                .innerJoin(accommodationAddress)
                .on(regionCondition)
                .leftJoin(reservation)
                .on(reservationCondition);

        if (numberOfPeople != null) {
            jpaQueryBase = jpaQueryBase.innerJoin(detailCondition)
                    .on(detailCondition.maxOfPeople.gt(numberOfPeople).or(detailCondition.maxOfPeople.eq(numberOfPeople)));
        }
        return ((JPAQuery) jpaQueryBase.where(reservation.id.isNull())).fetch();
    }

    public List<Accommodation> findByLocationCustom(double x, double y, double rangeKm,
                                                    LocalDate startDate, LocalDate endDate, Integer numberOfPeople) {
        String sql = "SELECT * " +
                "FROM accommodation a " +
                "JOIN accommodation_address b ON a.accommodation_address_id = b.id " +
                "AND MBRContains(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(',:x1,' ',:y1,',',:x2,' ',:y2,')')),b.location) " +
                "LEFT JOIN reservation c ON a.id = c.accommodation_id " +
                "AND ( " +
                "( " +
                ":start_date <= c.start_date AND c.start_date < :end_date) " +
                "OR (:start_date <c.end_date AND c.end_date <= :end_date) " +
                "OR (c.start_date < :start_date AND :end_date < c.end_date) " +
                ") ";
        if (numberOfPeople != null) {
            sql += "JOIN detail_condition d ON a.detail_condition_id = d.id AND d.max_of_people >= " + numberOfPeople + " ";
        }
        sql += "WHERE c.id IS NULL ";

        Rectangle2D rectangle2D = LocationUtils.getRectangle(x, y, rangeKm);
        double x1 = rectangle2D.getX();
        double y1 = rectangle2D.getY();
        double x2 = x1 + rectangle2D.getWidth();
        double y2 = y1 + rectangle2D.getHeight();

        Query nativeQuery = entityManager.createNativeQuery(sql, Accommodation.class)
                .setParameter("x1", x1)
                .setParameter("y1", y1)
                .setParameter("x2", x2)
                .setParameter("y2", y2)
                .setParameter("start_date", startDate)
                .setParameter("end_date", endDate);

        return nativeQuery.getResultList();
    }
}
