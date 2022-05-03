package core;

import br.com.sw2you.realmeet.Application;
import br.com.sw2you.realmeet.api.ApiClient;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;

@ActiveProfiles(profiles = "integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)//conectar em uma porta random para evitar conflitos
public abstract class BaseIntegrationTest {
    @Autowired//iniciar o flyway
    private Flyway flyway;

    @LocalServerPort //setar o valor da porta random
    private int serverPort;

    @BeforeEach
    void setup() throws Exception {
        setupFlyway();
//        mockApiKey();
        setupEach();
    }

    protected void setupEach() throws Exception {
    }

    //setar o endereço
    protected void setLocalHostBasePath(ApiClient apiClient, String path) throws MalformedURLException {
        apiClient.setBasePath(new URL("http", "localhost", serverPort, path).toString());
    }

    private void setupFlyway() {
        flyway.clean();
        flyway.migrate();
    }

//    private void mockApiKey() {
//        given(clientRepository.findById(TEST_CLIENT_API_KEY))
//                .willReturn(
//                        Optional.of(
//                                Client
//                                        .newBuilder()
//                                        .apiKey(TEST_CLIENT_API_KEY)
//                                        .description(TEST_CLIENT_DESCRIPTION)
//                                        .active(true)
//                                        .build()
//                        )
//                );
//    }
}
