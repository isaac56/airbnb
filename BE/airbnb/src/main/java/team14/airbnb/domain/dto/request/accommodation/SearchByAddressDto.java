package team14.airbnb.domain.dto.request.accommodation;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import team14.airbnb.exception.BadRequestException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
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

    public SearchByAddressDto(String regionDepth1, String regionDepth2, String regionDepth3, @NotNull(message = "체크인 날짜는 필수입니다.")
            LocalDate startDate, @NotNull(message = "체크아웃 날짜는 필수입니다.") LocalDate endDate, Integer minFee, Integer maxFee, Integer person) {
        if (regionDepth1 == null && regionDepth2 == null && regionDepth3 == null) {
            throw new BadRequestException("region_depth1, region_depth2, region_depth3 모두 null 일 수 없습니다.");
        }
        this.regionDepth1 = regionDepth1;
        this.regionDepth2 = regionDepth2;
        this.regionDepth3 = regionDepth3;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minFee = minFee;
        this.maxFee = maxFee;
        this.person = person;
    }
}
