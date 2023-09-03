package pl.eukon05.eventboard.integration.user;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.integration.AbstractIntegrationTest;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static pl.eukon05.eventboard.integration.IntegrationTestUtils.USER_ONE;
import static pl.eukon05.eventboard.integration.IntegrationTestUtils.USER_TWO;

class ManageFriendRequestIntegrationTests extends AbstractIntegrationTest {
    private static final String BEFRIEND_USER_TWO_URL = "user/userTwo/befriend";
    private static final String ACCEPT_USER_ONE_URL = "user/userOne/accept";
    private static final String REJECT_USER_ONE_URL = "user/userOne/reject";
    private static final String REQUESTS_URL = "user/friends/requests";


    @Test
    void should_accept_friend_request() {
        String tokenOne = utils.getToken(USER_ONE);
        sendFriendRequest(tokenOne);

        String tokenTwo = utils.getToken(USER_TWO);

        utils.sendAPIPostRequest(ACCEPT_USER_ONE_URL, tokenTwo)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("message", equalTo(Result.SUCCESS.getMessage()));

        checkThereAreNoRequests(tokenTwo);
    }

    @Test
    void should_reject_friend_request() {
        String tokenOne = utils.getToken(USER_ONE);
        sendFriendRequest(tokenOne);

        String tokenTwo = utils.getToken(USER_TWO);

        utils.sendAPIPostRequest(REJECT_USER_ONE_URL, tokenTwo)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("message", equalTo(Result.SUCCESS.getMessage()));

        checkThereAreNoRequests(tokenTwo);
    }

    @Test
    void should_not_accept_nonexistent_friend_request() {
        String tokenTwo = utils.getToken(USER_TWO);

        utils.sendAPIPostRequest(ACCEPT_USER_ONE_URL, tokenTwo)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo(Result.FRIEND_NOT_REQUESTED.getMessage()));
    }

    @Test
    void should_not_reject_nonexistent_friend_request() {
        String tokenTwo = utils.getToken(USER_TWO);

        utils.sendAPIPostRequest(REJECT_USER_ONE_URL, tokenTwo)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo(Result.FRIEND_NOT_REQUESTED.getMessage()));
    }

    private void sendFriendRequest(String token) {
        utils.sendAPIPostRequest(BEFRIEND_USER_TWO_URL, token);
    }

    private void checkThereAreNoRequests(String token) {
        utils.sendAPIGETRequest(REQUESTS_URL, token)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("data", equalTo(Collections.emptyList()));
    }
}
