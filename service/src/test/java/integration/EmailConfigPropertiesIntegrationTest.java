package integration;

import static br.com.sw2you.realmeet.config.properties.EmailConfigProperties.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import core.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class EmailConfigPropertiesIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private EmailConfigProperties emailConfigProperties;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.mail.smtp.port}")
    private String port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttlsEnabled;

    @Test
    void testLoadConfigProperties() {
        assertNotNull(host);
        assertEquals(host, emailConfigProperties.getHost());

        assertNotNull(username);
        assertEquals(username, emailConfigProperties.getUsername());

        assertNotNull(password);
        assertEquals(password, emailConfigProperties.getPassword());

        assertNotNull(from);
        assertEquals(from, emailConfigProperties.getFrom());

        assertNotNull(host);
        assertEquals(host, emailConfigProperties.getHost());

        assertNotNull(protocol);
        assertEquals(protocol, emailConfigProperties.getProperty(PROPERTY_TRANSPORT_PROTOCOL));

        assertNotNull(port);
        assertEquals(port, emailConfigProperties.getProperty(PROPERTY_SMTP_PORT));

        assertNotNull(auth);
        assertEquals(auth, emailConfigProperties.getProperty(PROPERTY_SMTP_AUTH));

        assertNotNull(starttlsEnabled);
        assertEquals(starttlsEnabled, emailConfigProperties.getProperty(PROPERTY_SMTP_STARTTLS_ENABLE));
    }
}