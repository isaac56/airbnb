package team14.airbnb.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer basicFee;

    private Integer weekendFee;

    private Integer cleaningFee;

    private String titleImageUrl;

    private String description;

    private Long hostId;
}
