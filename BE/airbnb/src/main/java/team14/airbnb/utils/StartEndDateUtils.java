package team14.airbnb.utils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StartEndDateUtils {
    private StartEndDateUtils() {
    }

    public static boolean isOverlapped(List<? extends StartEndDateAble> listOrderByStartDate, StartEndDateAble startEndDate) {
        if (startEndDate.getStartDate() == null || startEndDate.getEndDate() == null) {
            return true;
        }

        int index = Collections.binarySearch(listOrderByStartDate, startEndDate, Comparator.comparing(StartEndDateAble::getStartDate));
        if (index >= 0) {
            return true;
        }
        index = -(index + 1);
        if (index > 0) {
            LocalDate prevEndDate = listOrderByStartDate.get(index - 1).getEndDate();
            LocalDate currentStartDate = startEndDate.getStartDate();
            if (!prevEndDate.isBefore(currentStartDate)) {
                return true;
            }
        }
        if (listOrderByStartDate.size() > index) {
            LocalDate currentEndDate = startEndDate.getEndDate();
            LocalDate nextStartDate = listOrderByStartDate.get(index).getStartDate();
            if (!currentEndDate.isBefore(nextStartDate)) {
                return true;
            }
        }
        return false;
    }
}
