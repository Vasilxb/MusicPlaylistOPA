package mp.musicplaylist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import mp.musicplaylist.security.opa.OpaAuthorizationManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final OpaAuthorizationManager opaAuthorizationManager;

    public SecurityConfig(UserDetailsService userDetailsService, OpaAuthorizationManager opaAuthorizationManager) {
        this.userDetailsService = userDetailsService;
        this.opaAuthorizationManager = opaAuthorizationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headers) -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/h2-console/**"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/music-playlists",
                                "/songs"
                        ).permitAll()
                        .requestMatchers(
                                "/music-playlists/create",
                                "/music-playlists/save",
                                "/music-playlists/*/edit",
                                "/music-playlists/*/update",
                                "/music-playlists/*/delete"
                        ).access(opaAuthorizationManager)
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error=BadCredentials")
                        .defaultSuccessUrl("/music-playlists", true)
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                )
                .exceptionHandling((ex) -> ex
                        .accessDeniedPage("/access_denied")
                );

        http.userDetailsService(userDetailsService);
        return http.build();
    }
}
