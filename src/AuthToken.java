public class AuthToken {
    private String access_token;
    private String token_type;
    private int expires_in;

    public AuthToken() {
    }

    public AuthToken(String accessToken, String tokenType, int expiresIn) {
        this.access_token = accessToken;
        this.token_type = tokenType;
        this.expires_in = expiresIn;
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public String toString() {
        String str = "";
        str += "Token Type: " + token_type + ", ";
        str += "Expires in: " + expires_in + ", ";
        str += "Access Token: " + access_token;
        return str;
    }
}
