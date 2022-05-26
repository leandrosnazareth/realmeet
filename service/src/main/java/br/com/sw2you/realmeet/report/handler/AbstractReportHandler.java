package br.com.sw2you.realmeet.report.handler;

import br.com.sw2you.realmeet.email.TemplateType;
import br.com.sw2you.realmeet.report.enumeration.ReportFormat;
import br.com.sw2you.realmeet.report.enumeration.ReportHandlerType;
import br.com.sw2you.realmeet.report.model.AbstractReportData;
import br.com.sw2you.realmeet.report.validator.AbstractReportValidator;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import net.sf.jasperreports.engine.*;

public abstract class AbstractReportHandler<T, D extends AbstractReportData> {
    private final JasperReport jasperReport;

    public AbstractReportHandler(JasperReport jasperReport) {
        this.jasperReport = jasperReport;
    }

    public byte[] createReportBytes(D reportData, ReportFormat reportFormat) {
        var reportParams = new HashMap<String, Object>();
        var out = new ByteArrayOutputStream();

        fillReportParams(reportParams, reportData);

        try {
            var jasperPrint = JasperFillManager.fillReport(jasperReport, reportParams, getDataSource(reportData));
            exportReportToStream(jasperPrint, out, reportFormat);
            return out.toByteArray();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract TemplateType getTemplateType();

    public abstract AbstractReportValidator getReportValidator();

    public abstract ReportHandlerType getReportHandlerType();

    protected void fillReportParams(HashMap<String, Object> reportParams, D reportData) {}

    public JRDataSource getDataSource(D reportData) {
        return new JRDataSource() {
            private List<T> list;
            private int currentIndex = -1;

            @Override
            public boolean next() {
                if (currentIndex < 0) {
                    list = fetchReportData(reportData);
                }
                return ++currentIndex <= list.size() - 1;
            }

            @Override
            public Object getFieldValue(JRField jrField) {
                return fieldMapperFunction().apply(jrField, list.get(currentIndex));
            }
        };
    }

    protected abstract List<T> fetchReportData(D reportData);

    protected abstract BiFunction<JRField, T, Object> fieldMapperFunction();

    private void exportReportToStream(JasperPrint jasperPrint, OutputStream out, ReportFormat reportFormat) {
        try {
            switch (reportFormat) {
                case PDF:
                    JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                    break;
                case XML:
                    JasperExportManager.exportReportToXmlStream(jasperPrint, out);
                    break;
                default:
                    throw new IllegalArgumentException("Report type not supported: " + reportFormat.name());
            }
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
