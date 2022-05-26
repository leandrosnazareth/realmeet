package br.com.sw2you.realmeet.email;

public enum TemplateType {
    ALLOCATION_CREATED("allocationCreated"),
    ALLOCATION_UPDATED("allocationUpdated"),
    ALLOCATION_DELETED("allocationDeleted"),
    ALLOCATION_REPORT("allocationReport");

    String templateName;

    TemplateType(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
