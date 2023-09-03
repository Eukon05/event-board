package pl.eukon05.eventboard.integration.user;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import pl.eukon05.eventboard.integration.AbstractIntegrationTest;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static pl.eukon05.eventboard.integration.IntegrationTestUtils.USER_ONE;
import static pl.eukon05.eventboard.integration.IntegrationTestUtils.USER_TWO;

class GetFriendsIntegrationTests extends AbstractIntegrationTest {
    private static final String FRIENDS_URL = "user/friends";

    @Test
    void should_get_friends() {
        utils.makeUsersFriends();

        String tokenTwo = utils.getToken(USER_TWO);

        utils.sendAPIGETRequest(FRIENDS_URL, tokenTwo)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("data", equalTo(List.of(USER_ONE)));
    }

    @Test
    void should_get_empty_list() {
        String tokenTwo = utils.getToken(USER_TWO);

        utils.sendAPIGETRequest(FRIENDS_URL, tokenTwo)
                .statusCode(HttpStatus.SC_SUCCESS)
                .body("data", equalTo(Collections.emptyList()));
    }

}
