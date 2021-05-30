package team14.airbnb.domain.dto.request.wish;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WishAccommodationId {
    @NotNull(message = "찜 목록에서 제거할 숙소 id는 필수입니다.")
    private Long accommodationId;
}
