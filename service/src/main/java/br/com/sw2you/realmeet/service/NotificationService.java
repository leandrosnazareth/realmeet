package br.com.sw2you.realmeet.service;

import static br.com.sw2you.realmeet.email.TemplateType.*;
import static br.com.sw2you.realmeet.util.Constants.ALLOCATION;

import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.email.EmailInfoBuilder;
import br.com.sw2you.realmeet.email.EmailSender;
import br.com.sw2you.realmeet.email.TemplateType;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final EmailSender emailSender;
    private final EmailInfoBuilder emailInfoBuilder;

    public NotificationService(EmailSender emailSender, EmailInfoBuilder emailInfoBuilder) {
        this.emailSender = emailSender;
        this.emailInfoBuilder = emailInfoBuilder;
    }

    public void notifyAllocationCreated(Allocation allocation) {
        notify(allocation, ALLOCATION_CREATED);
    }

    public void notifyAllocationUpdated(Allocation allocation) {
        notify(allocation, ALLOCATION_UPDATED);
    }

    public void notifyAllocationDeleted(Allocation allocation) {
        notify(allocation, ALLOCATION_DELETED);
    }

    private void notify(Allocation allocation, TemplateType templateType) {
        emailSender.send(
                emailInfoBuilder.createEmailInfo(
                        allocation.getEmployee().getEmail(),
                        templateType,
                        Map.of(ALLOCATION, allocation)
                )
        );
    }
}