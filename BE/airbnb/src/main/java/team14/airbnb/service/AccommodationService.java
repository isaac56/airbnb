package team14.airbnb.service;

import org.springframework.stereotype.Service;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.request.accommodation.CreateDto;
import team14.airbnb.domain.dto.request.accommodation.SearchByLocationDto;
import team14.airbnb.domain.dto.response.accommodation.AccommodationSimpleDto;
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
                .filter(accommodation -> {
                    if (minFee != null && accommodation.getDailyFee() < minFee) {
                        return false;
                    }
                    if (maxFee != null && accommodation.getDailyFee() > maxFee) {
                        return false;
                    }
                    return true;
                })
                .map(accommodation -> AccommodationSimpleDto.of(accommodation, wishIdSet))
                .collect(Collectors.toList());
    }
}
