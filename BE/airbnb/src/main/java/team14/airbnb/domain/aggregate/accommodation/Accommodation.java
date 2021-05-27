package team14.airbnb.domain.aggregate.accommodation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Set<AccommodationOption> accommodationOptions = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_condition_id", nullable = false)
    private DetailCondition detailCondition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accommodation_address_id", nullable = false)
    private AccommodationAddress accommodationAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "accommodation_id", nullable = false)
    @OrderBy("startDate asc")
    private List<SpecialFee> specialFees = new ArrayList<>();

    public Accommodation(String name, int basicFee, Integer weekendFee, Integer cleaningFee, String titleImageUrl, String description, Long hostId
            , DetailCondition detailCondition, AccommodationAddress accommodationAddress) {
        this.name = name;
        this.basicFee = basicFee;
        this.weekendFee = weekendFee;
        this.cleaningFee = cleaningFee;
        this.titleImageUrl = titleImageUrl;
        this.description = description;
        this.hostId = hostId;
        this.detailCondition = detailCondition;
        this.accommodationAddress = accommodationAddress;
    }

    public void addAccommodationOption(String optionName) {
        this.accommodationOptions.add(new AccommodationOption(optionName));
    }

    public void addSpecialFee(LocalDate startDate, LocalDate endDate, int basicFee, Integer weekendFee) {
        this.specialFees.add(new SpecialFee(startDate, endDate, basicFee, weekendFee));
    }
}
