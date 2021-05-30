package team14.airbnb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.request.reservation.AccommodationReservationDto;
import team14.airbnb.domain.dto.response.ApiResult;
import team14.airbnb.domain.dto.response.reservation.BookedAccommodationDto;
import team14.airbnb.exception.NotFoundException;
import team14.airbnb.repository.UserRepository;
import team14.airbnb.service.ReservationService;

import javax.validation.Valid;
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
    public ApiResult checkIfReservationPossible(@Valid AccommodationReservationDto accommodationReservationDto) {
        long accommodationId = accommodationReservationDto.getAccommodationId();
        LocalDate startDate = accommodationReservationDto.getStartDate();
        LocalDate endDate = accommodationReservationDto.getEndDate();

        return ApiResult.succeed(reservationService.canMakeReservation(accommodationId, startDate, endDate));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult gerReservationList() {
        User user = getUser();
        List<BookedAccommodationDto> list = reservationService.getBookedAccommodations(user);
        return ApiResult.succeed(reservationService.getBookedAccommodations(user));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult makeReservation(@RequestBody @Valid AccommodationReservationDto accommodationReservationDto) {
        long accommodationId = accommodationReservationDto.getAccommodationId();
        LocalDate startDate = accommodationReservationDto.getStartDate();
        LocalDate endDate = accommodationReservationDto.getEndDate();
        User user = getUser();

        reservationService.makeReservation(accommodationId, startDate, endDate, user);
        return ApiResult.ok();
    }

}
