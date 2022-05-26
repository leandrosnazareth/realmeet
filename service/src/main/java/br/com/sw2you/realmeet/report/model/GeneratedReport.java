package br.com.sw2you.realmeet.report.model;

import br.com.sw2you.realmeet.email.TemplateType;
import br.com.sw2you.realmeet.report.enumeration.ReportFormat;
import java.util.Arrays;
import java.util.Objects;

public class GeneratedReport {
    private final byte[] bytes;
    private final ReportFormat reportFormat;
    private final String fileName;
    private final String email;
    private final TemplateType templateType;

    private GeneratedReport(Builder builder) {
        bytes = builder.bytes;
        reportFormat = builder.reportFormat;
        fileName = builder.fileName;
        email = builder.email;
        templateType = builder.templateType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    public String getFileName() {
        return fileName;
    }

    public String getEmail() {
        return email;
    }

    public TemplateType getTemplateType() {
        return templateType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedReport that = (GeneratedReport) o;
        return (
                Arrays.equals(bytes, that.bytes) &&
                        reportFormat == that.reportFormat &&
                        Objects.equals(fileName, that.fileName) &&
                        Objects.equals(email, that.email) &&
                        templateType == that.templateType
        );
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(reportFormat, fileName, email, templateType);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return (
                "GeneratedReport{" +
                        "bytes=" +
                        Arrays.toString(bytes) +
                        ", reportFormat=" +
                        reportFormat +
                        ", fileName='" +
                        fileName +
                        '\'' +
                        ", email='" +
                        email +
                        '\'' +
                        ", templateType=" +
                        templateType +
                        '}'
        );
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private byte[] bytes;
        private ReportFormat reportFormat;
        private String fileName;
        private String email;
        private TemplateType templateType;

        private Builder() {}

        public Builder bytes(byte[] bytes) {
            this.bytes = bytes;
            return this;
        }

        public Builder reportFormat(ReportFormat reportFormat) {
            this.reportFormat = reportFormat;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder templateType(TemplateType templateType) {
            this.templateType = templateType;
            return this;
        }

        public GeneratedReport build() {
            return new GeneratedReport(this);
        }
    }
}