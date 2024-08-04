package nextstep.oauth2.payload;

public class TokenRequest {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String code;


    public TokenRequest(final String clientId, final String clientSecret, final String redirectUri, final String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.code = code;
    }

    public static class AuthorizeRequestBuilder {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String code;


        public static AuthorizeRequestBuilder builder() {
            return new AuthorizeRequestBuilder();
        }

        public AuthorizeRequestBuilder clientId(final String clientId) {
            this.clientId = clientId;
            return this;
        }

        public AuthorizeRequestBuilder clientSecret(final String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public AuthorizeRequestBuilder redirectUri(final String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public AuthorizeRequestBuilder code(final String code) {
            this.code = code;
            return this;
        }

        public TokenRequest build() {
            return new TokenRequest(clientId, clientSecret, redirectUri, code);
        }

    }


}
