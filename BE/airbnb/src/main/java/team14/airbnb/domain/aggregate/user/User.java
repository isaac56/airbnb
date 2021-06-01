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

    private String oauthResourceServer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    @MapKeyColumn(name = "accommodation_id")
    private Map<Long, Wish> wishMap = new HashMap<>();

    public User(String email, String nickname, String oauthResourceServer) {
        this.email = email;
        this.nickname = nickname;
        this.oauthResourceServer = oauthResourceServer;
    }

    public void addWish(Accommodation accommodation) {
        this.wishMap.put(accommodation.getId(), new Wish(accommodation));
    }

    public void removeWish(long accommodationId) {
        this.wishMap.remove(accommodationId);
    }

    public Set<Long> getWishIdSet() {
        return wishMap.keySet();
    }

    public boolean hasWishedAccommodationId(long accommodationId) {
        return wishMap.containsKey(accommodationId);
    }
}
