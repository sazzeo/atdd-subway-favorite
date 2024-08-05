package nextstep.oauth2.payload;

public class AccessTokenResponse {
    private String access_token;

    private String scope;

    private String token_type;

    public AccessTokenResponse() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(final String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(final String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(final String token_type) {
        this.token_type = token_type;
    }
}
