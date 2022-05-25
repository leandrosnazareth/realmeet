package br.com.sw2you.realmeet.config;

import static br.com.sw2you.realmeet.config.properties.EmailConfigProperties.*;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {
    private final EmailConfigProperties emailConfigProperties;

    public EmailConfiguration(EmailConfigProperties emailConfigProperties) {
        this.emailConfigProperties = emailConfigProperties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfigProperties.getHost());
        mailSender.setPort(Integer.parseInt(emailConfigProperties.getProperty(PROPERTY_SMTP_PORT)));
        mailSender.setUsername(emailConfigProperties.getUsername());
        mailSender.setPassword(emailConfigProperties.getPassword());

        var properties = mailSender.getJavaMailProperties();
        properties.put(PROPERTY_TRANSPORT_PROTOCOL, emailConfigProperties.getProperty(PROPERTY_TRANSPORT_PROTOCOL));
        properties.put(PROPERTY_SMTP_AUTH, emailConfigProperties.getProperty(PROPERTY_SMTP_AUTH));
        properties.put(PROPERTY_SMTP_STARTTLS_ENABLE, emailConfigProperties.getProperty(PROPERTY_SMTP_STARTTLS_ENABLE));

        return mailSender;
    }
}
