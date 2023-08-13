package pl.eukon05.eventboard.integration.user;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.integration.AbstractIntegrationTest;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;

class RemoveFriendIntegrationTests extends AbstractIntegrationTest {

    private static final String REMOVE_URL = "/user/userTwo/defriend";
    private static final String FRIENDS_URL = "/user/friends";

    @Test
    void should_remove_friend() {
        makeUsersFriends();

        String tokenOne = getToken(USER_ONE);

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenOne)
                .when()
                .post(REMOVE_URL)
                .then()
                .statusCode(HttpStatus.SC_SUCCESS)
                .body(equalTo(Result.SUCCESS.getMessage()));

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenOne)
                .when()
                .get(FRIENDS_URL)
                .then()
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("", equalTo(Collections.emptyList()));
    }

    @Test
    void should_not_remove_non_friend() {
        String tokenOne = getToken(USER_ONE);

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenOne)
                .when()
                .post(REMOVE_URL)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(equalTo(Result.USER_NOT_FRIEND.getMessage()));
    }

}
