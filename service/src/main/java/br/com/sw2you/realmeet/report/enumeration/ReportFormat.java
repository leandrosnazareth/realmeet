package br.com.sw2you.realmeet.report.enumeration;

import java.util.Optional;

public enum ReportFormat {
    PDF("application/pdf"),
    XML("application/xml");

    String contentType;

    ReportFormat(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return "." + name().toLowerCase();
    }

    public static ReportFormat fromString(String reportFormatStr) {
        return Optional.ofNullable(reportFormatStr).map(t -> valueOf(reportFormatStr)).orElse(defaultReport());
    }

    private static ReportFormat defaultReport() {
        return PDF;
    }
}