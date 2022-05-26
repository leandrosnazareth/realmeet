package br.com.sw2you.realmeet.report.handler;

import static br.com.sw2you.realmeet.email.TemplateType.ALLOCATION_REPORT;
import static br.com.sw2you.realmeet.report.enumeration.ReportHandlerType.ALLOCATION;
import static br.com.sw2you.realmeet.util.Constants.EMPTY;
import static br.com.sw2you.realmeet.util.DateUtils.formatUsingDatePattern;
import static br.com.sw2you.realmeet.util.DateUtils.formatUsingDateTimePattern;

import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.email.TemplateType;
import br.com.sw2you.realmeet.report.enumeration.ReportHandlerType;
import br.com.sw2you.realmeet.report.model.AllocationReportData;
import br.com.sw2you.realmeet.report.validator.AbstractReportValidator;
import br.com.sw2you.realmeet.report.validator.AllocationReportValidator;
import java.time.OffsetTime;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AllocationReportHandler extends AbstractReportHandler<Allocation, AllocationReportData> {
    private static final String PARAM_DATE_FROM = "DateFromFilter";
    private static final String PARAM_DATE_TO = "DateToFilter";
    private static final String FIELD_DATE_FROM = "DateFrom";
    private static final String FIELD_DATE_TO = "DateTo";
    private static final String FIELD_ROOM_NAME = "RoomName";
    private static final String FIELD_EMPLOYEE_NAME = "EmployeeName";

    private final AllocationRepository allocationRepository;
    private final AllocationReportValidator allocationReportValidator;

    public AllocationReportHandler(
            @Qualifier("allocationReport") JasperReport jasperReport,
            AllocationRepository allocationRepository,
            AllocationReportValidator allocationReportValidator
    ) {
        super(jasperReport);
        this.allocationRepository = allocationRepository;
        this.allocationReportValidator = allocationReportValidator;
    }

    @Override
    protected void fillReportParams(HashMap<String, Object> reportParams, AllocationReportData reportData) {
        reportParams.put(PARAM_DATE_FROM, formatUsingDatePattern(reportData.getDateFrom()));
        reportParams.put(PARAM_DATE_TO, formatUsingDatePattern(reportData.getDateTo()));
    }

    @Override
    public TemplateType getTemplateType() {
        return ALLOCATION_REPORT;
    }

    @Override
    public AbstractReportValidator getReportValidator() {
        return allocationReportValidator;
    }

    @Override
    public ReportHandlerType getReportHandlerType() {
        return ALLOCATION;
    }

    @Override
    protected List<Allocation> fetchReportData(AllocationReportData reportData) {
        return allocationRepository.findAllWithFilters(
                null,
                null,
                reportData.getDateFrom().atTime(OffsetTime.MIN),
                reportData.getDateTo().atTime(OffsetTime.MAX)
        );
    }

    @Override
    protected BiFunction<JRField, Allocation, Object> fieldMapperFunction() {
        return (
                (jrField, allocation) -> {
                    switch (jrField.getName()) {
                        case FIELD_DATE_FROM:
                            return formatUsingDateTimePattern(allocation.getStartAt());
                        case FIELD_DATE_TO:
                            return formatUsingDateTimePattern(allocation.getEndAt());
                        case FIELD_ROOM_NAME:
                            return allocation.getRoom().getName();
                        case FIELD_EMPLOYEE_NAME:
                            return allocation.getEmployee().getName();
                        default:
                            return EMPTY;
                    }
                }
        );
    }
}
