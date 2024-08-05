package nextstep.oauth2;

import nextstep.oauth2.application.GithubOauth2Service;
import nextstep.oauth2.payload.AccessTokenRequest;
import nextstep.oauth2.payload.AccessTokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/login")
@RestController
public class OAuth2Controller {

    private final GithubOauth2Service githubOauth2Service;

    public OAuth2Controller(final GithubOauth2Service githubOauth2Service) {
        this.githubOauth2Service = githubOauth2Service;
    }

    @GetMapping("/code/github")
    public String code(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        var githubAccessTokenRequest = AccessTokenRequest.builder()
                .code(code)
                .clientId("")
                .clientSecret("")
                .build();

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(
                githubAccessTokenRequest, headers);

        String url = "https://github.com/login/oauth/access_token"; // github token request url
        var response = restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, AccessTokenResponse.class)
                .getBody();

        System.out.println(response);
        return "";
    }

}
