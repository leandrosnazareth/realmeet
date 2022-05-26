package integration;

import static br.com.sw2you.realmeet.email.TemplateType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.sw2you.realmeet.config.properties.TemplateConfigProperties;
import core.BaseIntegrationTest;
import br.com.sw2you.realmeet.email.TemplateType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class TemplateConfigPropertiesIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TemplateConfigProperties templateConfigProperties;

    @Value("${realmeet.email.templates.allocationCreated.subject}")
    private String allocationCreatedSubject;

    @Value("${realmeet.email.templates.allocationCreated.templateName}")
    private String allocationCreatedTemplateName;

    @Value("${realmeet.email.templates.allocationUpdated.subject}")
    private String allocationUpdatedSubject;

    @Value("${realmeet.email.templates.allocationUpdated.templateName}")
    private String allocationUpdatedTemplateName;

    @Value("${realmeet.email.templates.allocationDeleted.subject}")
    private String allocationDeletedSubject;

    @Value("${realmeet.email.templates.allocationDeleted.templateName}")
    private String allocationDeletedTemplateName;

    @Test
    void testLoadConfigProperties() {
        assertNotNull(allocationCreatedSubject);
        assertEquals(
                allocationCreatedSubject,
                templateConfigProperties.getEmailTemplate(ALLOCATION_CREATED).getSubject()
        );
        assertNotNull(allocationCreatedTemplateName);
        assertEquals(
                allocationCreatedTemplateName,
                templateConfigProperties.getEmailTemplate(ALLOCATION_CREATED).getTemplateName()
        );

        assertNotNull(allocationUpdatedSubject);
        assertEquals(
                allocationUpdatedSubject,
                templateConfigProperties.getEmailTemplate(ALLOCATION_UPDATED).getSubject()
        );
        assertNotNull(allocationUpdatedTemplateName);
        assertEquals(
                allocationUpdatedTemplateName,
                templateConfigProperties.getEmailTemplate(ALLOCATION_UPDATED).getTemplateName()
        );

        assertNotNull(allocationDeletedSubject);
        assertEquals(
                allocationDeletedSubject,
                templateConfigProperties.getEmailTemplate(ALLOCATION_DELETED).getSubject()
        );
        assertNotNull(allocationDeletedTemplateName);
        assertEquals(
                allocationDeletedTemplateName,
                templateConfigProperties.getEmailTemplate(ALLOCATION_DELETED).getTemplateName()
        );
    }
}
