package team14.airbnb.domain.aggregate.accommodation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int basicFee;

    private Integer weekendFee;

    private Integer cleaningFee;

    private String titleImageUrl;

    private String description;

    private Long hostId;

    @OneToMany(mappedBy = "accommodation")
    private Set<AccommodationOption> accommodationOptions = new HashSet<>();

    public Accommodation(String name, int basicFee, Integer weekendFee, Integer cleaningFee, String titleImageUrl, String description, Long hostId) {
        this.name = name;
        this.basicFee = basicFee;
        this.weekendFee = weekendFee;
        this.cleaningFee = cleaningFee;
        this.titleImageUrl = titleImageUrl;
        this.description = description;
        this.hostId = hostId;
    }

    public void addAccommodationOption(String optionName) {
        AccommodationOption accommodationOption = new AccommodationOption(optionName);
        accommodationOption.setAccommodation(this);
        this.accommodationOptions.add(accommodationOption);
    }
}
