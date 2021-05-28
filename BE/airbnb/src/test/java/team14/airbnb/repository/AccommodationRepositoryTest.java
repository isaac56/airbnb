package team14.airbnb.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team14.airbnb.domain.aggregate.accommodation.*;
import team14.airbnb.domain.aggregate.user.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Transactional
class AccommodationRepositoryTest {
    private UserRepository userRepository;
    private AccommodationRepository accommodationRepository;

    private Accommodation savedAccommodation;

    @Autowired
    public AccommodationRepositoryTest(UserRepository userRepository, AccommodationRepository accommodationRepository) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @BeforeEach
    void setUp() {
        User user = userRepository.save(new User("test@test.com"));
        Accommodation accommodation = new Accommodation("테스트 숙소", 65000, 85000, 10000,
                null, "테스트용 숙소입니다.", user,
                new DetailCondition(RoomType.ONE_ROOM, 4, 1, 1),
                new AccommodationAddress(
                        "충남 천안시 서북구 불당동 994",
                        "충남 천안시 서북구 불당2길 21",
                        "충남",
                        "천안시 서북구",
                        "불당",
                        127.107163314206,
                        36.8037190756251
                ));
        accommodation.addAccommodationOption("wifi");
        accommodation.addSpecialFee(new SpecialFee(LocalDate.of(2021, 12, 1), LocalDate.of(2021, 12, 31),
                20000, 30000));
        accommodation.addSpecialFee(new SpecialFee(LocalDate.of(2021, 10, 1), LocalDate.of(2021, 11, 30),
                20000, 30000));
        accommodation.addAccommodationImage("https://isaac56.github.io/public/img/Turtle.png");
        accommodation.addHashTag("테스트");
        accommodation.addHashTag("테스트");
        accommodation.addHashTag("확인");

        savedAccommodation = accommodationRepository.save(accommodation);
    }

    @Test
    @DisplayName("Accommodation과 연관 엔티티가 모두 정상적으로 저장되는지 확인")
    void findTest() {
        Accommodation accommodation = accommodationRepository.findById(savedAccommodation.getId()).orElseThrow(RuntimeException::new);
        Assertions.assertThat(accommodation.getAccommodationOptions().size()).isEqualTo(1);

        DetailCondition detailCondition = accommodation.getDetailCondition();
        Assertions.assertThat(detailCondition.getRoomType()).isEqualTo(RoomType.ONE_ROOM);
        Assertions.assertThat(detailCondition.getMaxOfPeople()).isEqualTo(4);
        Assertions.assertThat(detailCondition.getNumberOfRooms()).isEqualTo(1);
        Assertions.assertThat(detailCondition.getNumberOfToilet()).isEqualTo(1);

        AccommodationAddress accommodationAddress = accommodation.getAccommodationAddress();
        Assertions.assertThat(accommodationAddress.getAddressName()).isEqualTo("충남 천안시 서북구 불당동 994");

        List<SpecialFee> specialFeeList = accommodation.getSpecialFees();
        Assertions.assertThat(specialFeeList.get(0).getStartDate()).isEqualTo(LocalDate.of(2021, 10, 1));

        List<AccommodationImage> accommodationImages = accommodation.getAccommodationImages();
        Assertions.assertThat(accommodationImages.get(0).getImageUrl()).isEqualTo("https://isaac56.github.io/public/img/Turtle.png");

        Set<HashTag> hashTags = accommodation.getHashTags();
        Assertions.assertThat(hashTags.size()).isEqualTo(2);

        Assertions.assertThat(accommodation.getHost().getEmail()).isEqualTo("test@test.com");
    }
}
