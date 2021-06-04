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
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.accommodation.AccommodationAddress;
import team14.airbnb.domain.aggregate.accommodation.DetailCondition;
import team14.airbnb.domain.aggregate.accommodation.RoomType;
import team14.airbnb.domain.aggregate.reservation.Reservation;
import team14.airbnb.domain.aggregate.user.User;

import java.time.LocalDate;

@ComponentScan("team14.airbnb")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class ReservationRepositoryTest {
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;
    private long tempUserId;
    private long tempAccommodationId;
    private long tempReservationId;

    @Autowired
    public ReservationRepositoryTest(UserRepository userRepository, AccommodationRepository accommodationRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository = reservationRepository;
    }

    @BeforeEach
    void SetUp() {
        User user = userRepository.save(new User("host@host.com", "호스트닉네임", null));

        Accommodation accommodation = accommodationRepository.save(
                new Accommodation("테스트", 0, 0, 0, null, "", user,
                        new DetailCondition(RoomType.ONE_ROOM, 0, 0, 0),
                        new AccommodationAddress("test", "test", "", "", "", 0, 0)));

        Reservation reservation = reservationRepository.save(
                new Reservation(LocalDate.of(2021, 1, 15), LocalDate.of(2021, 1, 20), 100000, user, accommodation));

        this.tempUserId = user.getId();
        this.tempAccommodationId = accommodation.getId();
        this.tempReservationId = reservation.getId();
    }

    @Test
    @DisplayName("SetUp에서 저장한 Reservation이 잘 저장되고 잘 찾아지는지 테스트")
    void findTest() {
        Reservation reservation = reservationRepository.findById(tempReservationId).orElseThrow(RuntimeException::new);
        Assertions.assertThat(reservation.getAccommodation().getId()).isEqualTo(tempAccommodationId);
        Assertions.assertThat(reservation.getUser().getId()).isEqualTo(tempUserId);
    }

    @Test
    @DisplayName("Accommodation이 예약 가능한지 확인하는 테스트")
    void canMakeReservation() {
        LocalDate startDate = LocalDate.of(2021, 1, 13);
        LocalDate endDate = LocalDate.of(2021, 1, 15);
        Assertions.assertThat(reservationRepository.canMakeReservation(tempAccommodationId, startDate, endDate)).isTrue();

        startDate = LocalDate.of(2021, 1, 13);
        endDate = LocalDate.of(2021, 1, 16);
        Assertions.assertThat(reservationRepository.canMakeReservation(tempAccommodationId, startDate, endDate)).isFalse();

        startDate = LocalDate.of(2021, 1, 20);
        endDate = LocalDate.of(2021, 1, 21);
        Assertions.assertThat(reservationRepository.canMakeReservation(tempAccommodationId, startDate, endDate)).isTrue();

        startDate = LocalDate.of(2021, 1, 19);
        endDate = LocalDate.of(2021, 1, 21);
        Assertions.assertThat(reservationRepository.canMakeReservation(tempAccommodationId, startDate, endDate)).isFalse();

        startDate = LocalDate.of(2021, 1, 13);
        endDate = LocalDate.of(2021, 1, 21);
        Assertions.assertThat(reservationRepository.canMakeReservation(tempAccommodationId, startDate, endDate)).isFalse();
    }
}
