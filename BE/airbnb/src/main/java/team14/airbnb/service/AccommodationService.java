package team14.airbnb.service;

import org.springframework.stereotype.Service;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.request.accommodation.CreateDto;
import team14.airbnb.domain.dto.request.accommodation.SearchByAddressDto;
import team14.airbnb.domain.dto.request.accommodation.SearchByLocationDto;
import team14.airbnb.domain.dto.response.accommodation.AccommodationDetailDto;
import team14.airbnb.domain.dto.response.accommodation.AccommodationSimpleDto;
import team14.airbnb.exception.NotFoundException;
import team14.airbnb.repository.AccommodationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public Accommodation create(CreateDto createDto, User user) {
        return this.accommodationRepository.save(createDto.toEntity(user));
    }

    public List<AccommodationSimpleDto> searchByLocation(SearchByLocationDto searchByLocationDto, User user) {
        double x = searchByLocationDto.getX();
        double y = searchByLocationDto.getY();
        double rangeKm = searchByLocationDto.getRange();
        LocalDate startDate = searchByLocationDto.getStartDate();
        LocalDate endDate = searchByLocationDto.getEndDate();
        Integer person = searchByLocationDto.getPerson();
        Integer minFee = searchByLocationDto.getMinFee();
        Integer maxFee = searchByLocationDto.getMaxFee();

        Set<Long> wishIdSet = user.getWishIdSet();

        List<Accommodation> accommodationList = accommodationRepository.findByLocationCustom(x, y, rangeKm, startDate, endDate, person);
        accommodationList.stream().forEach(accommodation -> accommodation.setStartEndDate(startDate, endDate));

        return accommodationList.stream()
                .filter(accommodation -> accommodation.hasBetweenDailyFee(minFee, maxFee))
                .map(accommodation -> AccommodationSimpleDto.of(accommodation, wishIdSet))
                .collect(Collectors.toList());
    }

    public List<AccommodationSimpleDto> searchByAddress(SearchByAddressDto searchByAddressDto, User user) {
        String region1 = searchByAddressDto.getRegionDepth1();
        String region2 = searchByAddressDto.getRegionDepth2();
        String region3 = searchByAddressDto.getRegionDepth3();
        LocalDate startDate = searchByAddressDto.getStartDate();
        LocalDate endDate = searchByAddressDto.getEndDate();
        Integer person = searchByAddressDto.getPerson();
        Integer minFee = searchByAddressDto.getMinFee();
        Integer maxFee = searchByAddressDto.getMaxFee();

        Set<Long> wishIdSet = user.getWishIdSet();

        List<Accommodation> accommodationList = accommodationRepository.findByRegionsCustom(region1, region2, region3, startDate, endDate, person);
        accommodationList.stream().forEach(accommodation -> accommodation.setStartEndDate(startDate, endDate));

        return accommodationList.stream()
                .filter(accommodation -> accommodation.hasBetweenDailyFee(minFee, maxFee))
                .map(accommodation -> AccommodationSimpleDto.of(accommodation, wishIdSet))
                .collect(Collectors.toList());
    }

    public AccommodationDetailDto getDetailAccommodation(long id, LocalDate startDate, LocalDate endDate, User user) {
        Accommodation accommodation = accommodationRepository.findById(id).
                orElseThrow(() -> new NotFoundException(id + "에 해당하는 숙소가 없습니다."));
        accommodation.setStartEndDate(startDate, endDate);

        return AccommodationDetailDto.of(accommodation, user.getWishIdSet());
    }
}
