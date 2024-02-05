package pl.eukon05.eventboard.integration;

import com.tngtech.keycloakmock.api.KeycloakMock;
import com.tngtech.keycloakmock.api.ServerConfig;
import com.tngtech.keycloakmock.api.TokenConfig;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.TestComponent;
import pl.eukon05.eventboard.user.application.port.in.CreateUserInPort;
import pl.eukon05.eventboard.user.application.service.UserFacade;

@TestComponent
public final class IntegrationTestUtils {
    public static final String USER_ONE = "userOne";
    public static final String USER_TWO = "userTwo";
    private final UserFacade facade;
    private final KeycloakMock keycloakMock = new KeycloakMock(ServerConfig.aServerConfig().withNoContextPath().withPort(8180).withDefaultRealm("events").build());

    private IntegrationTestUtils(CreateUserInPort port, UserFacade facade) {
        keycloakMock.start();
        this.facade = facade;

        RestAssured.basePath += "/api/v1/";

        port.createUser(USER_ONE);
        port.createUser(USER_TWO);
    }

    public String getToken(String username) {
        return keycloakMock.getAccessToken(TokenConfig.aTokenConfig().withSubject(username).build());
    }

    public void makeUsersFriends() {
        facade.createFriendRequest(USER_ONE, USER_TWO);
        facade.acceptFriendRequest(USER_TWO, USER_ONE);
    }

    public ValidatableResponse sendAPIPostRequest(String URL, String token) {
        return sendAPIRequest(token)
                .post(URL)
                .then();
    }

    public ValidatableResponse sendAPIGETRequest(String URL, String token) {
        return sendAPIRequest(token)
                .get(URL)
                .then();
    }

    public RequestSpecification sendAPIRequest(String token) {
        return RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(token)
                .when();
    }

    public void cleanUp() {
        facade.rejectFriendRequest(USER_TWO, USER_ONE);
        facade.removeFriend(USER_ONE, USER_TWO); //We don't care about the result, if the users are not friends, nothing will happen
    }
}
