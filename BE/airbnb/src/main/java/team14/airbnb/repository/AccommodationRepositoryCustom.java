package team14.airbnb.repository;

import team14.airbnb.domain.aggregate.accommodation.Accommodation;

import java.time.LocalDate;
import java.util.List;

public interface AccommodationRepositoryCustom {

    List<Accommodation> findByRegionsCustom(String region1, String region2, String region3, LocalDate startDate, LocalDate endDate);

    List<Accommodation> findByLocationCustom(double x, double y, double rangeKm, LocalDate startDate, LocalDate endDate);
}
