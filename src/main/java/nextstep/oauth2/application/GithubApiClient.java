package nextstep.oauth2.application;

import nextstep.oauth2.payload.TokenRequest;

public interface GithubApiClient {
    void authorize(TokenRequest request);
}
