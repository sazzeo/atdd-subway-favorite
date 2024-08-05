package nextstep.oauth2.application;

import nextstep.oauth2.payload.AccessTokenResponse;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.stereotype.Component;

@Component
public class GithubApiClientImpl implements GithubApiClient {
    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final String clientId;
    private final String ClientSecret;

    public GithubApiClientImpl(final OAuth2ClientProperties oAuth2ClientProperties) {
        this.oAuth2ClientProperties = oAuth2ClientProperties;
        OAuth2ClientProperties.Registration github = oAuth2ClientProperties.getRegistration().get("github");
        this.clientId = github.getClientId();
        this.ClientSecret = github.getClientSecret();
    }

    @Override
    public AccessTokenResponse getAccessToken(String code) {
        return null;
    }
}
