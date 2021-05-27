package team14.airbnb.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static team14.airbnb.utils.StartEndDateUtils.isOverlapped;

class StartEndDateUtilsTest {
    private class StartDateTest implements StartEndDateAble {
        private LocalDate startDate;
        private LocalDate endDate;

        public StartDateTest(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public LocalDate getStartDate() {
            return startDate;
        }

        @Override
        public LocalDate getEndDate() {
            return endDate;
        }
    }

    List<StartDateTest> startDateTests;

    @BeforeEach
    void setUp() {
        startDateTests = new ArrayList<>();
        startDateTests.add(new StartDateTest(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 21)));
        startDateTests.add(new StartDateTest(LocalDate.of(2021, 2, 8), LocalDate.of(2021, 2, 19)));
        startDateTests.add(new StartDateTest(LocalDate.of(2021, 3, 4), LocalDate.of(2021, 3, 15)));
        startDateTests.add(new StartDateTest(LocalDate.of(2021, 6, 1), LocalDate.of(2021, 11, 21)));
    }

    @Test
    @DisplayName("startDate로 정렬된 상태에서 binarySearch를 사용하여 겹치는 기간인지 걸러내는 테스트")
    void isCanBeAddedTest() {
        Collections.sort(startDateTests, Comparator.comparing(StartDateTest::getStartDate));

        Assertions.assertThat(isOverlapped(startDateTests, new StartDateTest(LocalDate.of(2021, 1, 22), LocalDate.of(2021, 2, 7))))
                .isFalse();
        Assertions.assertThat(isOverlapped(startDateTests, new StartDateTest(LocalDate.of(2021, 1, 21), LocalDate.of(2021, 2, 7))))
                .isTrue();
        Assertions.assertThat(isOverlapped(startDateTests, new StartDateTest(LocalDate.of(2021, 1, 22), LocalDate.of(2021, 2, 8))))
                .isTrue();
        Assertions.assertThat(isOverlapped(startDateTests, new StartDateTest(LocalDate.of(2020, 12, 22), LocalDate.of(2021, 1, 1))))
                .isTrue();
        Assertions.assertThat(isOverlapped(startDateTests, new StartDateTest(LocalDate.of(2020, 12, 22), LocalDate.of(2020, 12, 31))))
                .isFalse();
        Assertions.assertThat(isOverlapped(startDateTests, new StartDateTest(LocalDate.of(2021, 11, 21), LocalDate.of(2021, 12, 8))))
                .isTrue();
        Assertions.assertThat(isOverlapped(startDateTests, new StartDateTest(LocalDate.of(2021, 11, 22), LocalDate.of(2021, 12, 8))))
                .isFalse();
    }

}
