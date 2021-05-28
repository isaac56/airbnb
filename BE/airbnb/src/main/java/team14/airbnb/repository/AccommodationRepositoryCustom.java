package team14.airbnb.repository;

import team14.airbnb.domain.aggregate.accommodation.Accommodation;

import java.util.List;

public interface AccommodationRepositoryCustom {

    List<Accommodation> findByRegionsCustom(String region1, String region2, String region3);
}
