package team14.airbnb.domain.dto.request.accommodation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.user.User;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateDto {
    @NotEmpty(message = "숙소 이름은 필수입니다.")
    private String name;

    @NotNull(message = "숙소 기본 가격은 필수입니다.")
    private Integer basicFee;

    private Integer weekendFee;

    private Integer cleaningFee;

    private String titleImageUrl;

    @NotEmpty(message = "숙소 설명은 필수입니다.")
    private String description;

    @NotNull(message = "숙소 주소는 필수입니다.")
    @Valid
    private AccommodationAddressDto address;

    @NotNull(message = "숙소 기본 정보는 필수입니다.")
    @Valid
    private DetailConditionDto detailCondition;

    private List<String> options;

    private List<String> imageUrls;

    private List<String> hashTags;

    public Accommodation toEntity(User host) {
        Accommodation accommodation = new Accommodation(
                name,
                basicFee,
                weekendFee,
                cleaningFee,
                titleImageUrl,
                description,
                host,
                detailCondition.toEntity(),
                address.toEntity()
        );
        if (options != null) {
            options.stream().forEach(option -> accommodation.addAccommodationOption(option));
        }
        if (imageUrls != null) {
            imageUrls.stream().forEach(url -> accommodation.addAccommodationImage(url));
        }
        if (hashTags != null) {
            hashTags.stream().forEach(hashTag -> accommodation.addHashTag(hashTag));
        }

        return accommodation;
    }
}
