package nextstep.oauth2.application;

import nextstep.oauth2.payload.TokenRequest;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubApiClientImpl implements GithubApiClient {

    private final OAuth2ClientProperties oAuth2ClientProperties;

    private final RestTemplate restTemplate;

    public GithubApiClientImpl(final OAuth2ClientProperties oAuth2ClientProperties, final RestTemplate restTemplate) {
        this.oAuth2ClientProperties = oAuth2ClientProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public void authorize(final TokenRequest request) {

    }
}
