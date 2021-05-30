package team14.airbnb.domain.aggregate.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team14.airbnb.domain.aggregate.accommodation.Accommodation;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    public static final String NOT_FOUND_MESSAGE = "사용자 정보를 찾을 수 없습니다";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @MapKeyColumn(name = "accommodation_id")
    private Map<Long, Wish> wishMap = new HashMap<>();

    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public void addWish(Accommodation accommodation) {
        this.wishMap.put(accommodation.getId(), new Wish(accommodation));
    }

    public Set<Long> getWishIdSet() {
        return wishMap.keySet();
    }
}
