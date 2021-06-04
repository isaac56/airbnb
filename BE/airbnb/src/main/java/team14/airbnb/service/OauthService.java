package team14.airbnb.service;

import org.springframework.stereotype.Service;

@Service
public interface OauthService {
    String getAccessToken(String authorizationCode);

    String getEmail(String accessToken);
}
