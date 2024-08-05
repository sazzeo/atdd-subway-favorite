package nextstep.oauth2.application;

import nextstep.oauth2.payload.AccessTokenResponse;

public interface GithubApiClient {
    AccessTokenResponse getAccessToken(String code);

}
