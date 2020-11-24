package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class Auth0ApiProvider {

    private final NotificationService notificationService;

    private final AuthSettings authSettings;

    private TokenHolder tokenHolder;

    public Auth0ApiProvider(NotificationService notificationService, AuthSettings authSettings) {
        this.notificationService = notificationService;
        this.authSettings = authSettings;
    }

    public AuthAPI getAuthApi() {
        return new AuthAPI(authSettings.getIssuer(), authSettings.getClientId(), authSettings.getClientSecret());
    }

    public ManagementAPI getManagementAPI() {
        return new ManagementAPI(authSettings.getIssuer(), getAccessToken());
    }

    private String getAccessToken() {
        if (tokenHolder == null || tokenHolder.getExpiresIn() <= 10) {
            requestNewToken();
        }
        return tokenHolder.getAccessToken();
    }

    private void requestNewToken() {
        try {
            AuthAPI authAPI = getAuthApi();
            AuthRequest authRequest = authAPI.requestToken(authSettings.getIssuer() + "api/v2/");
            this.tokenHolder = authRequest.execute();
        } catch (Auth0Exception auth0Exception) {
            notificationService.sendNotification(new Auth0ConnectionNotification(auth0Exception));
            throw new RuntimeException(auth0Exception.getMessage());
        }
    }
}
