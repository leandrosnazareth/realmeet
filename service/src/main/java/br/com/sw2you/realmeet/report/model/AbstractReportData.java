package br.com.sw2you.realmeet.report.model;

import java.util.Objects;

public abstract class AbstractReportData {
    private final String email;

    protected AbstractReportData(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractReportData that = (AbstractReportData) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "AbstractDataReport{email='" + email + '\'' + '}';
    }
}