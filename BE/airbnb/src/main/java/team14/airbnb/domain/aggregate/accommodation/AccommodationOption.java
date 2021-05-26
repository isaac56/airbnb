package team14.airbnb.domain.aggregate.accommodation;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name"})
public class AccommodationOption {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
