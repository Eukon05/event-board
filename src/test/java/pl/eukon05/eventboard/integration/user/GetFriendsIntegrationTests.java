package pl.eukon05.eventboard.integration.user;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import pl.eukon05.eventboard.integration.AbstractIntegrationTest;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

class GetFriendsIntegrationTests extends AbstractIntegrationTest {
    private static final String FRIENDS_URL = "user/friends";

    @Test
    void should_get_friends() {
        makeUsersFriends();

        String tokenTwo = getToken(USER_TWO);

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenTwo)
                .when()
                .get(FRIENDS_URL)
                .then()
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("", equalTo(List.of(USER_ONE)));
    }

    @Test
    void should_get_empty_list() {
        String tokenTwo = getToken(USER_TWO);

        RestAssured.given()
                .auth()
                .preemptive()
                .oauth2(tokenTwo)
                .when()
                .get(FRIENDS_URL)
                .then()
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("", equalTo(Collections.emptyList()));
    }

}
