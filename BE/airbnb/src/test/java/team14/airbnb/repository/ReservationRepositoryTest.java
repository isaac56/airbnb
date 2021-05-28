package team14.airbnb.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.accommodation.AccommodationAddress;
import team14.airbnb.domain.aggregate.accommodation.DetailCondition;
import team14.airbnb.domain.aggregate.accommodation.RoomType;
import team14.airbnb.domain.aggregate.reservation.Reservation;
import team14.airbnb.domain.aggregate.user.User;

import java.time.LocalDate;

@SpringBootTest
@Transactional
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
        User user = userRepository.save(new User("host@host.com"));

        Accommodation accommodation = accommodationRepository.save(
                new Accommodation("테스트", 0, 0, 0, null, "", user,
                        new DetailCondition(RoomType.ONE_ROOM, 0, 0, 0),
                        new AccommodationAddress("test", "test", "", "", "", 0, 0)));

        Reservation reservation = reservationRepository.save(
                new Reservation(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 3), 100000, user, accommodation));

        this.tempUserId = user.getId();
        this.tempAccommodationId = accommodation.getId();
        this.tempReservationId = reservation.getId();
    }

    @Test
    @DisplayName("SetUp에서 저장한 Reservation이 잘 저장되고 잘 찾아지는지 테스트")
    void findTest() {
        Reservation reservation = reservationRepository.findById(tempReservationId).orElseThrow(RuntimeException::new);
        Assertions.assertThat(reservation.getAccommodation().getId()).isEqualTo(tempReservationId);
        Assertions.assertThat(reservation.getUser().getId()).isEqualTo(tempUserId);
    }
}
