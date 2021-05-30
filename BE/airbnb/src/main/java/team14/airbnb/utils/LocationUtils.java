package team14.airbnb.utils;

import java.awt.geom.Rectangle2D;

public class LocationUtils {
    private LocationUtils() {

    }

    public static Rectangle2D getRectangle(double x, double y, double rangeKm) {
        final double R = 6371; // 지구 반지름 km

        double x1 = (x - Math.toDegrees(rangeKm / R / Math.cos(Math.toRadians(y))));
        double x2 = (x + Math.toDegrees(rangeKm / R / Math.cos(Math.toRadians(y))));
        double y1 = (y + Math.toDegrees(rangeKm / R));
        double y2 = (y - Math.toDegrees(rangeKm / R));

        double left = x1 < x2 ? x1 : x2;
        double right = x1 > x2 ? x1 : x2;
        double up = y1 < y1 ? y1 : y2;
        double down = y1 > y2 ? y1 : y2;

        return new Rectangle2D.Double(left, up, right - left, down - up);
    }
}
