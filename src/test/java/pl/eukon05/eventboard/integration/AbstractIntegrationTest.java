package pl.eukon05.eventboard.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@Testcontainers
@Import(IntegrationTestUtils.class)
public abstract class AbstractIntegrationTest {
    @Autowired
    protected IntegrationTestUtils utils;

    @Container
    private static final GenericContainer<BaeldungPostgresqlContainer> postgres = BaeldungPostgresqlContainer.getInstance();

    @BeforeAll
    static void setUp(@LocalServerPort int port) {
        RestAssured.port = port;
    }

    @BeforeEach
    void cleanUp() {
        utils.cleanUp();
    }
}
