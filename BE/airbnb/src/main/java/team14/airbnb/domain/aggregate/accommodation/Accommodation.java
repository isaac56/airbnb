package team14.airbnb.domain.aggregate.accommodation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team14.airbnb.domain.aggregate.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static team14.airbnb.utils.StartEndDateUtils.getIndexToInsert;
import static team14.airbnb.utils.StartEndDateUtils.isOverlapped;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Set<AccommodationOption> accommodationOptions = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_condition_id", nullable = false)
    private DetailCondition detailCondition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accommodation_address_id", nullable = false)
    private AccommodationAddress accommodationAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accommodation_id", nullable = false)
    @OrderBy("startDate asc")
    private List<SpecialFee> specialFees = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private List<AccommodationImage> accommodationImages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Set<HashTag> hashTags = new HashSet<>();

    @Transient
    private LocalDate startDate;

    @Transient
    private LocalDate endDate;

    @Transient
    private Integer totalFee;

    @Transient
    private Integer dailyFee;

    public Accommodation(String name, int basicFee, Integer weekendFee, Integer cleaningFee, String titleImageUrl, String description, User host
            , DetailCondition detailCondition, AccommodationAddress accommodationAddress) {
        this.name = name;
        this.basicFee = basicFee;
        this.weekendFee = weekendFee;
        this.cleaningFee = cleaningFee;
        this.titleImageUrl = titleImageUrl;
        this.description = description;
        this.host = host;
        this.detailCondition = detailCondition;
        this.accommodationAddress = accommodationAddress;
    }

    public void addAccommodationOption(String optionName) {
        this.accommodationOptions.add(new AccommodationOption(optionName));
    }

    public void addSpecialFee(SpecialFee specialFee) {
        if (isOverlapped(specialFees, specialFee)) {
            throw new RuntimeException("이미 설정된 특별가격과 기간이 겹칩니다.");
        }
        this.specialFees.add(getIndexToInsert(specialFees, specialFee), specialFee);
    }

    public void addAccommodationImage(String imageUrl) {
        this.accommodationImages.add(new AccommodationImage(imageUrl));
    }

    public void addHashTag(String name) {
        this.hashTags.add(new HashTag(name));
    }

    public void setStartEndDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalFee = getTotalFee(startDate, endDate);

        this.dailyFee = (totalFee - cleaningFee) / (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    private int getTotalFee(LocalDate startDate, LocalDate endDate) {
        return getBetweenFee(startDate, endDate, basicFee, weekendFee) + cleaningFee;
    }

    private int getBetweenFee(LocalDate startDate, LocalDate endDate, int basicFee, Integer weekendFee) {
        int weekendRealFee = weekendFee != null ? weekendFee : basicFee;
        int weekFee = basicFee * 5 + weekendRealFee * 2;
        int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate);
        if (totalDays < 0) {
            return -1;
        }

        int betweenFee = 0;
        betweenFee += (totalDays / 7) * weekFee;

        int startDayOfWeek = startDate.getDayOfWeek().ordinal();
        int endDayOfWeek = endDate.getDayOfWeek().ordinal();

        if (startDayOfWeek <= endDayOfWeek) {
            betweenFee += getBetweenFeeInWeekend(startDayOfWeek, endDayOfWeek, basicFee, weekendRealFee);
            return betweenFee;
        }

        betweenFee += getBetweenFeeBeyondWeekend(startDayOfWeek, endDayOfWeek, basicFee, weekendRealFee);
        return betweenFee;
    }

    private int getBetweenFeeInWeekend(int startDaysOfWeek, int endDayOfWeek, int basicFee, int weekendFee) {
        int[] feeOfDayOfWeek = new int[]{basicFee, basicFee, basicFee, basicFee, weekendFee, weekendFee, basicFee};
        int betweenFee = 0;
        for (int dayOfWeek = startDaysOfWeek; dayOfWeek < endDayOfWeek; dayOfWeek++) {
            betweenFee += feeOfDayOfWeek[dayOfWeek];
        }
        return betweenFee;
    }

    private int getBetweenFeeBeyondWeekend(int startDayOfWeek, int endDayOfWeek, int basicFee, int weekendFee) {
        int[] feeOfDayOfWeek = new int[]{basicFee, basicFee, basicFee, basicFee, weekendFee, weekendFee, basicFee};
        int betweenFee = 0;
        for (int dayOfWeek = startDayOfWeek; dayOfWeek < feeOfDayOfWeek.length; dayOfWeek++) {
            betweenFee += feeOfDayOfWeek[dayOfWeek];
        }
        for (int dayOfWeek = 0; dayOfWeek < endDayOfWeek; dayOfWeek++) {
            betweenFee += feeOfDayOfWeek[dayOfWeek];
        }
        return betweenFee;
    }

    public String getAddressName() {
        if (accommodationAddress == null) {
            return "주소 정보가 없습니다.";
        }
        return accommodationAddress.getAddressName();
    }

    public String getRoadAddressName() {
        if (accommodationAddress == null) {
            return "주소 정보가 없습니다.";
        }
        return accommodationAddress.getRoadAddressName();
    }

    public Double getX() {
        if (accommodationAddress == null) {
            return null;
        }
        return accommodationAddress.getLocation().getX();
    }

    public Double getY() {
        if (accommodationAddress == null) {
            return null;
        }
        return accommodationAddress.getLocation().getY();
    }

    public String getTypeName() {
        if (detailCondition == null) {
            return "숙소 상세 정보가 없습니다.";
        }
        return detailCondition.getRoomType().getKorean();
    }

    public Integer getMaxOfPerson() {
        if (detailCondition == null) {
            return null;
        }
        return detailCondition.getMaxOfPeople();
    }

    public Integer getNumberOfRoom() {
        if (detailCondition == null) {
            return null;
        }
        return detailCondition.getNumberOfRooms();
    }

    public Integer getNumberOfToilet() {
        if (detailCondition == null) {
            return null;
        }
        return detailCondition.getNumberOfToilet();
    }

    public boolean hasBetweenDailyFee(Integer minFee, Integer maxFee) {
        if (this.dailyFee == null) {
            return false;
        }

        if (minFee != null && dailyFee < minFee) {
            return false;
        }
        if (maxFee != null && dailyFee > maxFee) {
            return false;
        }
        return true;
    }
}
