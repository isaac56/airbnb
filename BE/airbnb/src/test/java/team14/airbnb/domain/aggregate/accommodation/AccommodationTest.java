package team14.airbnb.domain.aggregate.accommodation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team14.airbnb.domain.aggregate.user.User;

import java.time.LocalDate;

class AccommodationTest {
    Accommodation accommodation;

    @BeforeEach
    void setUp() {
        accommodation = new Accommodation("테스트", 100000, 120000, 2000, "imageurl", "설명", new User("test", "test"),
                new DetailCondition(RoomType.ONE_ROOM, 3, 1, 1), null);
    }

    @Test
    @DisplayName("기간 설정 후에 총 요금이 제대로 계산되는지 확인")
    void totalFeeTest() {
        //실제 자는 요일은 금,토,일,월,화,수 요금은 240000 + 400000 + 2000 = 642000원 나와야함
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 1, 7);
        accommodation.setStartEndDate(startDate, endDate);
        Assertions.assertThat(accommodation.getTotalFee()).isEqualTo(642000);

        //실제 자는 요일은 금,토,일,월,화,수,목 요금은 240000 + 500000 + 2000 = 742000원 나와야함
        startDate = LocalDate.of(2021, 1, 1);
        endDate = LocalDate.of(2021, 1, 8);
        accommodation.setStartEndDate(startDate, endDate);
        Assertions.assertThat(accommodation.getTotalFee()).isEqualTo(742000);

        //실제 자는 요일은 금,토,일,월,화,수,목,금,토,일,월,화 요금은 480000 + 800000 + 2000 = 1282000원 나와야함
        startDate = LocalDate.of(2021, 1, 1);
        endDate = LocalDate.of(2021, 1, 13);
        accommodation.setStartEndDate(startDate, endDate);
        Assertions.assertThat(accommodation.getTotalFee()).isEqualTo(1282000);
    }
}
