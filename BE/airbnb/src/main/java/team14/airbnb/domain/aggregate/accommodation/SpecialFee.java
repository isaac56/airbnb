package team14.airbnb.domain.aggregate.accommodation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team14.airbnb.utils.StartEndDateAble;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpecialFee implements StartEndDateAble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int basicFee;

    private Integer weekendFee;

    public SpecialFee(LocalDate startDate, LocalDate endDate, int basicFee, Integer weekendFee) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.basicFee = basicFee;
        this.weekendFee = weekendFee;
    }
}
