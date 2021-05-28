package team14.airbnb.domain.aggregate.accommodation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressName;

    private String roadAddressName;

    @Column(name = "region1_depth_name")
    private String region1DepthName;

    @Column(name = "region2_depth_name")
    private String region2DepthName;

    @Column(name = "region3_depth_name")
    private String region3DepthName;

    private Point location;

    public AccommodationAddress(String addressName, String roadAddressName, String region1DepthName, String region2DepthName, String region3DepthName,
                                double x, double y) {
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.region1DepthName = region1DepthName;
        this.region2DepthName = region2DepthName;
        this.region3DepthName = region3DepthName;
        String pointWkt = String.format("POINT(%s %s)", x, y);
        try {
            this.location = (Point) new WKTReader().read(pointWkt);
        } catch (ParseException parseException) {
            throw new RuntimeException("숙소의 위치값이 잘못되었습니다.");
        }
    }
}
