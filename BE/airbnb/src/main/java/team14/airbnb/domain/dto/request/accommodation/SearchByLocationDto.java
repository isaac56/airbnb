package team14.airbnb.domain.dto.request.accommodation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SearchByLocationDto {
    @NotNull(message = "기준 경도값은 필수입니다.")
    private Double x;

    @NotNull(message = "기준 위도값은 필수입니다.")
    private Double y;

    @NotNull(message = "범위(km)값은 필수입니다.")
    private Double range;

    @NotNull(message = "체크인 날짜는 필수입니다.")
    private LocalDate startDate;

    @NotNull(message = "체크아웃 날짜는 필수입니다.")
    private LocalDate endDate;

    private Integer minFee;

    private Integer maxFee;

    private Integer person;
}
