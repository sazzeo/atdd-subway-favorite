package nextstep.oauth2.application;

import org.springframework.stereotype.Service;

@Service
public class GithubOauth2Service {

    private final GithubApiClient githubApiClient;

    public GithubOauth2Service(final GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    public void getAccessToken(final String code) {
        githubApiClient.getAccessToken(code);
    }
}
