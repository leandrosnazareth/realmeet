package integration;

import static br.com.sw2you.realmeet.email.TemplateType.*;
import static br.com.sw2you.realmeet.util.Constants.ALLOCATION;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static utils.TestDataCreator.newAllocationBuilder;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import br.com.sw2you.realmeet.config.properties.TemplateConfigProperties;
import core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.email.EmailSender;
import br.com.sw2you.realmeet.email.TemplateType;
import br.com.sw2you.realmeet.service.NotificationService;
import org.junit.jupiter.api.Test;
import utils.TestDataCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class NotificationServiceIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private NotificationService victim;

    @Autowired
    private TemplateConfigProperties templateConfigProperties;

    @Autowired
    private EmailConfigProperties emailConfigProperties;

    @MockBean
    private EmailSender emailSender;

    private Allocation allocation;

    @Override
    protected void setupEach() throws Exception {
        allocation = newAllocationBuilder(TestDataCreator.newRoomBuilder().build()).build();
    }

    @Test
    void testNotifyAllocationCreated() {
        victim.notifyAllocationCreated(allocation);
        testInteraction(ALLOCATION_CREATED);
    }

    @Test
    void testNotifyAllocationUpdated() {
        victim.notifyAllocationUpdated(allocation);
        testInteraction(ALLOCATION_UPDATED);
    }

    @Test
    void testNotifyAllocationDeleted() {
        victim.notifyAllocationDeleted(allocation);
        testInteraction(ALLOCATION_DELETED);
    }

    private void testInteraction(TemplateType templateType) {
        var emailTemplate = templateConfigProperties.getEmailTemplate(templateType);
        verify(emailSender)
                .send(
                        argThat(
                                emailInfo ->
                                        emailInfo.getSubject().equals(emailTemplate.getSubject()) &&
                                                emailInfo.getTemplate().equals(emailTemplate.getTemplateName()) &&
                                                emailInfo.getTo().get(0).equals(allocation.getEmployee().getEmail()) &&
                                                emailInfo.getFrom().equals(emailConfigProperties.getFrom()) &&
                                                emailInfo.getTemplateData().get(ALLOCATION).equals(allocation)
                        )
                );
    }
}