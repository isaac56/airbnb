package team14.airbnb.service;

import org.springframework.stereotype.Service;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.request.accommodation.AccommodationCreateDto;
import team14.airbnb.repository.AccommodationRepository;

@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public Accommodation create(AccommodationCreateDto accommodationCreateDto, User user) {
        return this.accommodationRepository.save(accommodationCreateDto.toEntity(user));
    }
}
