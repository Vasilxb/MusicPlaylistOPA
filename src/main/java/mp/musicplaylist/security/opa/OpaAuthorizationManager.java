package mp.musicplaylist.security.opa;

import org.springframework.http.MediaType;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class OpaAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    private final RestClient opaRestClient;
    private final OpaAuthorizationProperties properties;

    public OpaAuthorizationManager(
            RestClient opaRestClient,
            OpaAuthorizationProperties properties
    ) {
        this.opaRestClient = opaRestClient;
        this.properties = properties;
    }

    @Override
    public AuthorizationResult authorize(
            Supplier<? extends Authentication> authentication,
            RequestAuthorizationContext context
    ) {
        Authentication auth = authentication.get();
        var request = context.getRequest();

        // TEMPORARY DEBUG
        System.out.println("========== OPA AUTH ==========");
        System.out.println("Authentication: " + auth);
        System.out.println("Name: " + (auth == null ? "NULL" : auth.getName()));
        System.out.println("Authenticated: " +
                (auth != null && auth.isAuthenticated()));
        System.out.println("Authorities: " +
                (auth == null ? "NULL" : auth.getAuthorities()));
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("METHOD: " + request.getMethod());
        System.out.println("==============================");

        List<String> authorities =
                auth == null
                        ? List.of()
                        : auth.getAuthorities()
                        .stream()
                        .map(grantedAuthority ->
                                grantedAuthority.getAuthority()
                        )
                        .toList();

        String principal =
                auth == null
                        ? "anonymousUser"
                        : auth.getName();

        Map<String, Object> payload = Map.of(
                "input", Map.of(
                        "principal", principal,
                        "authorities", authorities,
                        "uri", request.getRequestURI(),
                        "method", request.getMethod(),
                        "headers", Map.of(
                                "Accept",
                                request.getHeader("Accept") == null
                                        ? ""
                                        : request.getHeader("Accept")
                        )
                )
        );

        Map<String, Object> response = opaRestClient
                .post()
                .uri(properties.getPolicyPath())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(Map.class);

        boolean allowed = false;

        if (response != null) {
            Object resultObject = response.get("result");

            if (resultObject instanceof Map<?, ?> result) {
                allowed = Boolean.TRUE.equals(
                        result.get("authorized")
                );
            }
        }

        final boolean finalAllowed = allowed;

        return new AuthorizationResult() {
            @Override
            public boolean isGranted() {
                return finalAllowed;
            }
        };
    }

//    public void verify(
//            Supplier<? extends Authentication> authentication,
//            RequestAuthorizationContext context
//    ) {
//        AuthorizationResult result =
//                authorize(authentication, context);
//
//        if (!result.isGranted()) {
//            throw new AuthorizationDeniedException(
//                    "OPA authorization denied the request.",
//                    result
//            );
//        }
//    }
}
