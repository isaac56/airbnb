package team14.airbnb.service;

import org.springframework.stereotype.Service;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.response.accommodation.AccommodationSimpleDto;
import team14.airbnb.exception.BadRequestException;
import team14.airbnb.exception.NotFoundException;
import team14.airbnb.repository.AccommodationRepository;
import team14.airbnb.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public WishService(UserRepository userRepository, AccommodationRepository accommodationRepository) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
    }

    public List<AccommodationSimpleDto> getWishList(User user) {
        return user.getWishMap().values().stream().sorted((a, b) -> -a.getCreatedTime().compareTo(b.getCreatedTime())).
                map(wish -> AccommodationSimpleDto.of(wish.getAccommodation(), true))
                .collect(Collectors.toList());
    }

    public void deleteWish(long accommodationId, User user) {
        if (!user.hasWishedAccommodationId(accommodationId)) {
            throw new NotFoundException(accommodationId + "번 숙소는 찜 목록에 존재하지 않습니다.");
        }
        user.removeWish(accommodationId);
        userRepository.save(user);
    }

    public void addWish(long accommodationId, User user) {
        if (user.hasWishedAccommodationId(accommodationId)) {
            throw new BadRequestException(accommodationId + "번 숙소는 이미 찜 목록에 존재합니다.");
        }
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException(accommodationId + "에 해당하는 숙소가 없습니다."));

        user.addWish(accommodation);
        userRepository.save(user);
    }
}
