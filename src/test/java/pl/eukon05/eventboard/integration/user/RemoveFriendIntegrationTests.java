package pl.eukon05.eventboard.integration.user;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import pl.eukon05.eventboard.common.Result;
import pl.eukon05.eventboard.integration.AbstractIntegrationTest;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static pl.eukon05.eventboard.integration.IntegrationTestUtils.USER_ONE;

class RemoveFriendIntegrationTests extends AbstractIntegrationTest {

    private static final String REMOVE_URL = "/user/userTwo/defriend";
    private static final String FRIENDS_URL = "/user/friends";

    @Test
    void should_remove_friend() {
        utils.makeUsersFriends();

        String tokenOne = utils.getToken(USER_ONE);

        utils.sendAPIPostRequest(REMOVE_URL, tokenOne)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("message", equalTo(Result.SUCCESS.getMessage()));

        utils.sendAPIGETRequest(FRIENDS_URL, tokenOne)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("data", equalTo(Collections.emptyList()));
    }

    @Test
    void should_not_remove_non_friend() {
        String tokenOne = utils.getToken(USER_ONE);

        utils.sendAPIPostRequest(REMOVE_URL, tokenOne)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo(Result.USER_NOT_FRIEND.getMessage()));
    }

}
