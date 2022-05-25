package br.com.sw2you.realmeet.email;

import static br.com.sw2you.realmeet.util.StringUtils.join;
import static java.util.Objects.nonNull;

import br.com.sw2you.realmeet.email.model.Attachment;
import br.com.sw2you.realmeet.email.model.EmailInfo;
import br.com.sw2you.realmeet.exception.EmailSendingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);//logs das informações de envio
    private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=UTF-8";

    private final JavaMailSender javaMailSender;
    private final ITemplateEngine templateEngine;

    public EmailSender(JavaMailSender javaMailSender, ITemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void send(EmailInfo emailInfo) {
        LOGGER.info("Envio de e-mail com assunto '{}' to '{}'", emailInfo.getSubject(), emailInfo.getTo());

        var mimeMessage = javaMailSender.createMimeMessage();
        var multipart = new MimeMultipart();
        //add as partes do e-mail
        addBasicDetails(emailInfo, mimeMessage);
        addHtmlBody(emailInfo.getTemplate(), emailInfo.getTemplateData(), multipart);
        addAttachments(emailInfo.getAttachments(), multipart);
        setContent(mimeMessage, multipart);

        javaMailSender.send(mimeMessage);
    }

    private void addBasicDetails(EmailInfo emailInfo, MimeMessage mimeMessage) {
        try {
            mimeMessage.setFrom(emailInfo.getFrom());
            mimeMessage.setSubject(emailInfo.getSubject());
            mimeMessage.addRecipients(Message.RecipientType.TO, join(emailInfo.getTo()));

            if (nonNull(emailInfo.getCc())) {
                mimeMessage.addRecipients(Message.RecipientType.CC, join(emailInfo.getCc()));
            }

            if (nonNull(emailInfo.getBcc())) {
                mimeMessage.addRecipients(Message.RecipientType.BCC, join(emailInfo.getBcc()));
            }
        } catch (MessagingException e) {
            throwEmailSendingException(e, "Erro ao adicionar dados à mensagem MIME");
        }
    }

    private void addHtmlBody(String template, Map<String, Object> templateData, MimeMultipart multipart) {
        var messageHtmlPart = new MimeBodyPart();
        var context = new Context();

        if (nonNull(templateData)) {
            context.setVariables(templateData);
        }

        try {
            messageHtmlPart.setContent(templateEngine.process(template, context), TEXT_HTML_CHARSET_UTF_8);
            multipart.addBodyPart(messageHtmlPart);
        } catch (Exception e) {
            throwEmailSendingException(e, "Error adding HTML content to MIME Message");
        }
    }

    //add os anexos
    private void addAttachments(List<Attachment> attachments, MimeMultipart multipart) {
        if (nonNull(attachments)) {//pode ser nulo
            attachments.forEach(
                    a -> {
                        try {
                            var messageAttachmentPart = new MimeBodyPart();
                            messageAttachmentPart.setDataHandler(
                                    new DataHandler(new ByteArrayDataSource(a.getInputStream(), a.getContentType()))
                            );
                            messageAttachmentPart.setFileName(a.getFileName());
                            multipart.addBodyPart(messageAttachmentPart);
                        } catch (MessagingException | IOException e) {
                            throwEmailSendingException(e, "Error adding attachments to MIME Message");
                        }
                    }
            );
        }
    }

    private void setContent(MimeMessage mimeMessage, MimeMultipart multipart) {
        try {
            mimeMessage.setContent(multipart);
        } catch (MessagingException e) {
            throwEmailSendingException(e, "Error setting content to MIME Message");
        }
    }

    private void throwEmailSendingException(Exception exception, String errorMessage) {
        var fullErrorMessage = String.format("%s: %s", exception.getMessage(), errorMessage);
        LOGGER.error(fullErrorMessage);
        throw new EmailSendingException(fullErrorMessage, exception);
    }
}
