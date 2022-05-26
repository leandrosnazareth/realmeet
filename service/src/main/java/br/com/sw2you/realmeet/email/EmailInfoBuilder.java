package br.com.sw2you.realmeet.email;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import br.com.sw2you.realmeet.config.properties.TemplateConfigProperties;
import br.com.sw2you.realmeet.email.model.Attachment;
import br.com.sw2you.realmeet.email.model.EmailInfo;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EmailInfoBuilder {
    private final EmailConfigProperties emailConfigProperties;
    private final TemplateConfigProperties templateConfigProperties;

    public EmailInfoBuilder(
            EmailConfigProperties emailConfigProperties,
            TemplateConfigProperties templateConfigProperties
    ) {
        this.emailConfigProperties = emailConfigProperties;
        this.templateConfigProperties = templateConfigProperties;
    }

    public EmailInfo createEmailInfo(String email, TemplateType templateType, Map<String, Object> templateData) {
        return createEmailInfo(email, templateType, templateData, null);
    }

    public EmailInfo createEmailInfo(
            String email,
            TemplateType templateType,
            Map<String, Object> templateData,
            List<Attachment> attachments
    ) {
        var emailTemplate = templateConfigProperties.getEmailTemplate(templateType);
        return EmailInfo
                .newBuilder()
                .from(emailConfigProperties.getFrom())
                .subject(emailTemplate.getSubject())
                .to(List.of(email))
                .template(emailTemplate.getTemplateName())
                .templateData(templateData)
                .attachments(attachments)
                .build();
    }
}