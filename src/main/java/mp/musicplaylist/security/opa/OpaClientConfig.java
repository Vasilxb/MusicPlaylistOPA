package mp.musicplaylist.security.opa;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(OpaAuthorizationProperties.class)
public class OpaClientConfig {
    @Bean
    public RestClient opaRestClient(OpaAuthorizationProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
