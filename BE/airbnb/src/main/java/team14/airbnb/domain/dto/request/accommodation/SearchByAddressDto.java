package team14.airbnb.domain.dto.request.accommodation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SearchByAddressDto {
    private String regionDepth1;

    private String regionDepth2;

    private String regionDepth3;

    @NotNull(message = "체크인 날짜는 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "체크아웃 날짜는 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Integer minFee;

    private Integer maxFee;

    private Integer person;
}
