package br.com.sw2you.realmeet.email.model;

import java.io.InputStream;
import java.util.Objects;

public class Attachment {
    private final InputStream inputStream;//stream de dados
    private final String contentType;
    private final String fileName;//nome do arquivo

    private Attachment(Builder builder) {
        inputStream = builder.inputStream;
        contentType = builder.contentType;
        fileName = builder.fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return (
                Objects.equals(inputStream, that.inputStream) &&
                        Objects.equals(contentType, that.contentType) &&
                        Objects.equals(fileName, that.fileName)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputStream, contentType, fileName);
    }

    @Override
    public String toString() {
        return (
                "Attachment{" +
                        "inputStream=" +
                        inputStream +
                        ", contentType='" +
                        contentType +
                        '\'' +
                        ", fileName='" +
                        fileName +
                        '\'' +
                        '}'
        );
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private InputStream inputStream;
        private String contentType;
        private String fileName;

        private Builder() {}

        public Builder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Attachment build() {
            return new Attachment(this);
        }
    }
}