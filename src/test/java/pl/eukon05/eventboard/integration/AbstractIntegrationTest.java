package pl.eukon05.eventboard.integration;

import com.tngtech.keycloakmock.api.KeycloakMock;
import com.tngtech.keycloakmock.api.ServerConfig;
import com.tngtech.keycloakmock.api.TokenConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.eukon05.eventboard.user.application.port.in.CreateUserPort;
import pl.eukon05.eventboard.user.application.service.UserFacade;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@Testcontainers
public abstract class AbstractIntegrationTest {

    //To allow for modifying DB data to prepare it for tests
    @Autowired
    private UserFacade facade;

    protected static String USER_ONE = "userOne";
    protected static String USER_TWO = "userTwo";

    @Container
    private static final GenericContainer<BaeldungPostgresqlContainer> postgres = BaeldungPostgresqlContainer.getInstance();
    private static final KeycloakMock keycloakMock = new KeycloakMock(ServerConfig.aServerConfig().withNoContextPath().withPort(8180).withDefaultRealm("events").build());

    @BeforeAll
    public static void setUp(@Autowired CreateUserPort createUserPort, @LocalServerPort int localServerPort) {
        keycloakMock.start();
        RestAssured.basePath += "/api/v1/";

        createUserPort.createUser(USER_ONE);
        createUserPort.createUser(USER_TWO);

        RestAssured.port = localServerPort;
    }

    @BeforeEach
    public void cleanUp() {
        facade.rejectFriendRequest(USER_TWO, USER_ONE);
        facade.removeFriend(USER_ONE, USER_TWO); //We don't care about the result, if the users are not friends, nothing will happen
    }

    protected String getToken(String username) {
        return keycloakMock.getAccessToken(TokenConfig.aTokenConfig().withSubject(username).build());
    }

    protected void makeUsersFriends() {
        facade.createFriendRequest(USER_ONE, USER_TWO);
        facade.acceptFriendRequest(USER_TWO, USER_ONE);
    }

}
