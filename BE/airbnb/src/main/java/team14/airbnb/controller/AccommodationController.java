package team14.airbnb.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.request.accommodation.CreateDto;
import team14.airbnb.domain.dto.request.accommodation.SearchByAddressDto;
import team14.airbnb.domain.dto.request.accommodation.SearchByLocationDto;
import team14.airbnb.domain.dto.response.ApiResult;
import team14.airbnb.exception.NotFoundException;
import team14.airbnb.repository.UserRepository;
import team14.airbnb.service.AccommodationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/accommodation")
public class AccommodationController {
    private final AccommodationService accommodationService;
    //로그인 구현 전까지 사용하기 위한 임시 코드
    private final UserRepository userRepository;

    public AccommodationController(AccommodationService accommodationService, UserRepository userRepository) {
        this.accommodationService = accommodationService;
        this.userRepository = userRepository;
    }

    //로그인 구현 전까지 사용하기 위한 임시 코드
    private User getUser() {
        return userRepository.findById(1l).orElseThrow(() -> new NotFoundException(User.NOT_FOUND_MESSAGE));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult create(@RequestBody @Valid CreateDto createDto) {
        User user = getUser(); //로그인 구현 전까지 사용하기 위한 임시 코드
        accommodationService.create(createDto, user);
        return ApiResult.ok();
    }

    @GetMapping("/list/location")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult searchByLocation(@Valid SearchByLocationDto searchByLocationDto) {
        User user = getUser();
        return ApiResult.succeed(accommodationService.searchByLocation(searchByLocationDto, user));
    }

    @GetMapping("/list/region")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult searchByDetailAddress(@Valid SearchByAddressDto searchByAddressDto) {
        User user = getUser();
        return ApiResult.succeed(accommodationService.searchByAddress(searchByAddressDto, user));
    }

    @GetMapping("/{accommodationId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult getDetailAccommodation(
            @PathVariable @NotNull(message = "숙소 id는 필수입니다.") Long accommodationId,
            @NotNull(message = "체크인 시간은 필수입니다.") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @NotNull(message = "체크아웃 시간은 필수입니다.") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        User user = getUser();
        return ApiResult.succeed(accommodationService.getDetailAccommodation(accommodationId, startDate, endDate, user));
    }
}
