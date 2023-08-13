package pl.eukon05.eventboard.integration.user;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.integration.AbstractIntegrationTest;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;

class ManageFriendRequestIntegrationTests extends AbstractIntegrationTest {
    private static final String BEFRIEND_USER_TWO_URL = "user/userTwo/befriend";
    private static final String ACCEPT_USER_ONE_URL = "user/userOne/accept";
    private static final String REJECT_USER_ONE_URL = "user/userOne/reject";
    private static final String REQUESTS_URL = "user/friends/requests";


    @Test
    void should_accept_friend_request() {
        String tokenOne = getToken(USER_ONE);
        sendFriendRequest(tokenOne);

        String tokenTwo = getToken(USER_TWO);

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenTwo)
                .when()
                .post(ACCEPT_USER_ONE_URL)
                .then()
                .statusCode(HttpStatus.SC_SUCCESS)
                .body(equalTo(Result.SUCCESS.getMessage()));

        checkThereAreNoRequests(tokenTwo);
    }

    @Test
    void should_reject_friend_request() {
        String tokenOne = getToken(USER_ONE);
        sendFriendRequest(tokenOne);

        String tokenTwo = getToken(USER_TWO);

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenTwo)
                .when()
                .post(REJECT_USER_ONE_URL)
                .then()
                .statusCode(HttpStatus.SC_SUCCESS)
                .body(equalTo(Result.SUCCESS.getMessage()));

        checkThereAreNoRequests(tokenTwo);
    }

    @Test
    void should_not_accept_nonexistent_friend_request() {
        String tokenTwo = getToken(USER_TWO);

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenTwo)
                .when()
                .post(ACCEPT_USER_ONE_URL)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(equalTo(Result.FRIEND_NOT_REQUESTED.getMessage()));
    }

    @Test
    void should_not_reject_nonexistent_friend_request() {
        String tokenTwo = getToken(USER_TWO);

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenTwo)
                .when()
                .post(REJECT_USER_ONE_URL)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(equalTo(Result.FRIEND_NOT_REQUESTED.getMessage()));
    }

    private void sendFriendRequest(String token) {
        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(token)
                .post(BEFRIEND_USER_TWO_URL);
    }

    private void checkThereAreNoRequests(String token) {
        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(token)
                .when()
                .get(REQUESTS_URL)
                .then()
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("", equalTo(Collections.emptyList()));
    }
}
