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
import team14.airbnb.domain.aggregate.user.User;

@ComponentScan("team14.airbnb")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class UserRepositoryTest {
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private long tempUserId;
    private long tempAccommodationId;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, AccommodationRepository accommodationRepository) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @BeforeEach
    void setUp() {
        User user = userRepository.save(new User("host@host.com", "호스트닉네임", null));

        Accommodation accommodation = accommodationRepository.save(
                new Accommodation("테스트", 0, 0, 0, null, "", user,
                        new DetailCondition(RoomType.ONE_ROOM, 0, 0, 0),
                        new AccommodationAddress("test", "test", "", "", "", 0, 0)));

        user.addWish(accommodation);
        this.tempUserId = user.getId();
        this.tempAccommodationId = accommodation.getId();
    }

    @Test
    @DisplayName("setUp에서 저장한 user가 잘 찾아지는지 확인")
    void findTest() {
        User user = userRepository.findById(this.tempUserId).orElseThrow(RuntimeException::new);

        Assertions.assertThat(user.getEmail()).isEqualTo("host@host.com");
        Assertions.assertThat(user.getWishMap().get(tempAccommodationId).getAccommodation().getName()).isEqualTo("테스트");
    }
}
