package team14.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team14.airbnb.domain.dto.response.ApiResult;
import team14.airbnb.service.OauthService;
import team14.airbnb.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final OauthService oauthService;

    @Autowired
    public UserController(UserService userService, OauthService oauthService) {
        this.userService = userService;
        this.oauthService = oauthService;
    }

    @GetMapping("/login/oauth/github")
    public ApiResult loginUsingGithub(String code) {
        String accessToken = oauthService.getAccessToken(code);
        String email = oauthService.getEmail(accessToken);

        return ApiResult.succeed(userService.signIn(email, "GITHUB"));
    }
}
