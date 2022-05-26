package br.com.sw2you.realmeet.config.properties.model;

import java.util.Objects;

public class EmailTemplate {
    private final String subject;
    private final String templateName;

    public EmailTemplate(String subject, String templateName) {
        this.subject = subject;
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailTemplate that = (EmailTemplate) o;
        return Objects.equals(subject, that.subject) && Objects.equals(templateName, that.templateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, templateName);
    }

    @Override
    public String toString() {
        return "EmailTemplate{subject='" + subject + '\'' + ", templateName='" + templateName + '\'' + '}';
    }
}
