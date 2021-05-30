package team14.airbnb.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team14.airbnb.domain.dto.response.ApiResult;
import team14.airbnb.service.ReservationService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/possible")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult checkIfReservationPossible(
            @NotNull(message = "숙소 id는 필수입니다.") Long accommodationId,
            @NotNull(message = "체크인 시간은 필수입니다.") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @NotNull(message = "체크아웃 시간은 필수입니다.") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        return ApiResult.succeed(reservationService.canMakeReservation(accommodationId, startDate, endDate));
    }
}
