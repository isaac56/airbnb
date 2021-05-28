package team14.airbnb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.request.accommodation.AccommodationCreateDto;
import team14.airbnb.domain.dto.response.ApiResult;
import team14.airbnb.exception.NotFoundException;
import team14.airbnb.repository.UserRepository;
import team14.airbnb.service.AccommodationService;

import javax.validation.Valid;

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
    public ApiResult create(@RequestBody @Valid AccommodationCreateDto accommodationCreateDto) {
        User user = getUser(); //로그인 구현 전까지 사용하기 위한 임시 코드
        accommodationService.create(accommodationCreateDto, user);
        return ApiResult.ok();
    }
}
