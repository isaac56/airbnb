package team14.airbnb.domain.aggregate.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    private LocalDateTime createdTime;

    public Wish(Accommodation accommodation) {
        this.accommodation = accommodation;
        this.createdTime = LocalDateTime.now();
    }
}
