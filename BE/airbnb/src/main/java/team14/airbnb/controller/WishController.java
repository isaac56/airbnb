package team14.airbnb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team14.airbnb.domain.aggregate.user.User;
import team14.airbnb.domain.dto.request.wish.WishAccommodationId;
import team14.airbnb.domain.dto.response.ApiResult;
import team14.airbnb.exception.NotFoundException;
import team14.airbnb.repository.UserRepository;
import team14.airbnb.service.WishService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/wish")
public class WishController {
    private final WishService wishService;
    //로그인 구현 전까지 사용하기 위한 임시 코드
    private final UserRepository userRepository;

    public WishController(WishService wishService, UserRepository userRepository) {
        this.wishService = wishService;
        this.userRepository = userRepository;
    }

    //로그인 구현 전까지 사용하기 위한 임시 코드
    private User getUser() {
        return userRepository.findById(1l).orElseThrow(() -> new NotFoundException(User.NOT_FOUND_MESSAGE));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult getWishList() {
        User user = getUser();

        return ApiResult.succeed(wishService.getWishList(user));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult addWish(@RequestBody @Valid WishAccommodationId wishAccommodationId) {
        User user = getUser();

        wishService.addWish(wishAccommodationId.getAccommodationId(), user);
        return ApiResult.ok();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResult deleteWish(@RequestBody @Valid WishAccommodationId wishAccommodationId) {
        User user = getUser();

        wishService.deleteWish(wishAccommodationId.getAccommodationId(), user);
        return ApiResult.ok();
    }
}
