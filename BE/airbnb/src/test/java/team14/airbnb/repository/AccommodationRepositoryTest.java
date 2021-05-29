package team14.airbnb.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import team14.airbnb.domain.aggregate.accommodation.*;
import team14.airbnb.domain.aggregate.reservation.Reservation;
import team14.airbnb.domain.aggregate.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@ComponentScan("team14.airbnb")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class AccommodationRepositoryTest {
    private UserRepository userRepository;
    private AccommodationRepository accommodationRepository;
    private ReservationRepository reservationRepository;

    private Accommodation savedAccommodation;

    @Autowired
    public AccommodationRepositoryTest(UserRepository userRepository, AccommodationRepository accommodationRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository = reservationRepository;
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

        LocalDate startDate = LocalDate.of(2020, 01, 01);
        LocalDate endDate = LocalDate.of(2020, 01, 10);
        reservationRepository.save(new Reservation(startDate, endDate, 100000, user, accommodation));
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

    @Test
    @DisplayName("region_depth_name으로 검색하는 custom Query 테스트")
    void findByRegionCustom() {
        //예약된 날짜는 01/01~01/10이므로 아래 테스트는 지역 검색만 확인하기 위함
        LocalDate startDate = LocalDate.of(2020, 01, 10);
        LocalDate endDate = LocalDate.of(2020, 01, 20);
        Assertions.assertThat(accommodationRepository.
                findByRegionsCustom("충남", "천안시 서북구", "불당", startDate, endDate)).isNotEmpty();
        Assertions.assertThat(accommodationRepository.
                findByRegionsCustom("충남", "천안시 서북구", "??", startDate, endDate)).isEmpty();
        Assertions.assertThat(accommodationRepository.
                findByRegionsCustom("충남", null, null, startDate, endDate)).isNotEmpty();
        Assertions.assertThat(accommodationRepository.
                findByRegionsCustom(null, "천안시 서북구", null, startDate, endDate)).isNotEmpty();
        Assertions.assertThat(accommodationRepository.
                findByRegionsCustom(null, "??", null, startDate, endDate)).isEmpty();
        Assertions.assertThat(accommodationRepository.
                findByRegionsCustom(null, null, "불당", startDate, endDate)).isNotEmpty();

        //startDate 와 endDate 사이에 예약이 되어있으면 검색에서 제외되는지 확인
        startDate = LocalDate.of(2019, 12, 31);
        endDate = LocalDate.of(2020, 01, 11);
        Assertions.assertThat(accommodationRepository.
                findByRegionsCustom(null, null, "불당", startDate, endDate)).isEmpty();
    }

    @Test
    @DisplayName("location으로 검색하는 custom Query 테스트")
    void findByLocationCustom() {
        //예약된 날짜는 01/01~01/10이므로 아래 테스트는 지역 검색만 확인하기 위함
        LocalDate startDate = LocalDate.of(2020, 01, 10);
        LocalDate endDate = LocalDate.of(2020, 01, 20);
        //testX = 127.073, testY = 36.8037190756251 는 기준 데이터 POINT(127.107163314206 36.8037190756251)와
        //ST_DISTANCE_SPHERE 값으로 3041미터 차이나는 상태임
        double testX = 127.073;
        double testY = 36.8037190756251;

        Assertions.assertThat(accommodationRepository.findByLocationCustom(testX, testY, 3.042, startDate, endDate)).isNotEmpty();
        Assertions.assertThat(accommodationRepository.findByLocationCustom(testX, testY, 3.040, startDate, endDate)).isEmpty();

        //startDate 와 endDate 사이에 예약이 되어있으면 검색에서 제외되는지 확인
        startDate = LocalDate.of(2019, 12, 31);
        endDate = LocalDate.of(2020, 01, 11);
        Assertions.assertThat(accommodationRepository.
                findByLocationCustom(testX, testY, 3.042, startDate, endDate)).isEmpty();
    }
}
