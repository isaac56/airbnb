package team14.airbnb.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.response.ApiResult;
import team14.airbnb.domain.dto.response.reservation.BookedAccommodationDto;
import team14.airbnb.exception.NotFoundException;
import team14.airbnb.repository.UserRepository;
import team14.airbnb.service.ReservationService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    //로그인 구현 전까지 사용하기 위한 임시 코드
    private final UserRepository userRepository;

    public ReservationController(ReservationService reservationService, UserRepository userRepository) {
        this.reservationService = reservationService;
        this.userRepository = userRepository;
    }

    //로그인 구현 전까지 사용하기 위한 임시 코드
    private User getUser() {
        return userRepository.findById(1l).orElseThrow(() -> new NotFoundException(User.NOT_FOUND_MESSAGE));
    }

    @GetMapping("/possible")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult checkIfReservationPossible(
            @NotNull(message = "숙소 id는 필수입니다.") Long accommodationId,
            @NotNull(message = "체크인 시간은 필수입니다.") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @NotNull(message = "체크아웃 시간은 필수입니다.") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        return ApiResult.succeed(reservationService.canMakeReservation(accommodationId, startDate, endDate));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult gerReservationList() {
        User user = getUser();
        List<BookedAccommodationDto> list = reservationService.getBookedAccommodations(user);
        return ApiResult.succeed(reservationService.getBookedAccommodations(user));
    }

}
