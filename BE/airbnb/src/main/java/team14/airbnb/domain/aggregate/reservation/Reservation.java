package team14.airbnb.domain.aggregate.reservation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;
import team14.airbnb.domain.aggregate.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int totalFee;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    private LocalDateTime createdTime;

    public Reservation(LocalDate startDate, LocalDate endDate, int totalFee, User user, Accommodation accommodation) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalFee = totalFee;
        this.user = user;
        this.accommodation = accommodation;
        this.createdTime = LocalDateTime.now();
        this.deleted = false;
    }

    public Long getAccommodationId() {
        if (this.accommodation == null) {
            return null;
        }
        return accommodation.getId();
    }

    public Accommodation getAccommodation() {
        if (accommodation != null) {
            accommodation.setStartEndDate(this.startDate, this.endDate);
        }
        return this.accommodation;
    }
}
