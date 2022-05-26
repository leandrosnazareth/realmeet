package br.com.sw2you.realmeet.config.properties;

import br.com.sw2you.realmeet.config.properties.model.EmailTemplate;
import br.com.sw2you.realmeet.email.TemplateType;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "realmeet.email")
@ConstructorBinding
public class TemplateConfigProperties {
    private final Map<String, EmailTemplate> templates;

    public TemplateConfigProperties(Map<String, EmailTemplate> templates) {
        this.templates = templates;
    }

    public EmailTemplate getEmailTemplate(TemplateType templateType) {
        return templates.get(templateType.getTemplateName());
    }
}
