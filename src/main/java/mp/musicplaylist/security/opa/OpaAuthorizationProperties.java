package mp.musicplaylist.security.opa;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.opa")
public class OpaAuthorizationProperties {
    private String baseUrl = "http://localhost:8181";
    private String policyPath = "/v1/data/auth/playlist";

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPolicyPath() {
        return policyPath;
    }

    public void setPolicyPath(String policyPath) {
        this.policyPath = policyPath;
    }
}
