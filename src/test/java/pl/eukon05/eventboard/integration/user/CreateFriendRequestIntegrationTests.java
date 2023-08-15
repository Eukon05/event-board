package pl.eukon05.eventboard.integration.user;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.integration.AbstractIntegrationTest;

import static org.hamcrest.Matchers.equalTo;
import static pl.eukon05.eventboard.integration.IntegrationTestUtils.USER_ONE;
import static pl.eukon05.eventboard.integration.IntegrationTestUtils.USER_TWO;

class CreateFriendRequestIntegrationTests extends AbstractIntegrationTest {

    private static final String BEFRIEND_USER_TWO_URL = "user/userTwo/befriend";
    private static final String REQUESTS_URL = "user/friends/requests";

    @Test
    void should_create_friend_request() {
        String tokenOne = utils.getToken(USER_ONE);
        String tokenTwo = utils.getToken(USER_TWO);

        utils.sendAPIPostRequest(BEFRIEND_USER_TWO_URL, tokenOne)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body(equalTo(Result.SUCCESS.getMessage()));

        utils.sendAPIGETRequest(REQUESTS_URL, tokenTwo)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("[0]", equalTo(USER_ONE));
    }

    @Test
    void should_not_befriend_friend() {
        utils.makeUsersFriends();

        String tokenOne = utils.getToken(USER_ONE);

        utils.sendAPIPostRequest(BEFRIEND_USER_TWO_URL, tokenOne)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(equalTo(Result.USER_ALREADY_FRIEND.getMessage()));
    }

    @Test
    void should_not_befriend_user_twice() {
        String tokenOne = utils.getToken(USER_ONE);

        utils.sendAPIPostRequest(BEFRIEND_USER_TWO_URL, tokenOne)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body(equalTo(Result.SUCCESS.getMessage()));

        utils.sendAPIPostRequest(BEFRIEND_USER_TWO_URL, tokenOne)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(equalTo(Result.FRIEND_REQUEST_ALREADY_SENT.getMessage()));
    }

}
