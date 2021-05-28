package team14.airbnb.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.accommodation.QAccommodation;
import team14.airbnb.domain.aggregate.accommodation.QAccommodationAddress;

import java.util.List;

public class AccommodationRepositoryCustomImpl implements AccommodationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public AccommodationRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Accommodation> findByRegionsCustom(String region1, String region2, String region3) {
        QAccommodation accommodation = QAccommodation.accommodation;
        QAccommodationAddress accommodationAddress = QAccommodationAddress.accommodationAddress;

        BooleanBuilder builder = new BooleanBuilder();
        if (region1 != null && !region1.isEmpty()) {
            builder.and(accommodationAddress.region1DepthName.eq(region1));
        }
        if (region2 != null && !region2.isEmpty()) {
            builder.and(accommodationAddress.region2DepthName.eq(region2));
        }
        if (region3 != null && !region3.isEmpty()) {
            builder.and(accommodationAddress.region3DepthName.eq(region3));
        }

        return jpaQueryFactory.selectFrom(accommodation)
                .innerJoin(accommodation.accommodationAddress, accommodationAddress)
                .on(builder)
                .fetch();
    }
}
