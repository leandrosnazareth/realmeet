package br.com.sw2you.realmeet.report.resolver;

import br.com.sw2you.realmeet.report.enumeration.ReportHandlerType;
import br.com.sw2you.realmeet.report.handler.AbstractReportHandler;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ReportHandlerResolver {
    private final Set<AbstractReportHandler> reportHandlers;
    private final Map<ReportHandlerType, AbstractReportHandler> reportHandlersMap;

    public ReportHandlerResolver(Set<AbstractReportHandler> reportHandlers) {
        this.reportHandlers = reportHandlers;
        reportHandlersMap = new ConcurrentHashMap<>();
    }

    public AbstractReportHandler resolveReportHandler(ReportHandlerType reportHandlerType) {
        return reportHandlersMap.computeIfAbsent(reportHandlerType, this::findReportHandler);
    }

    private AbstractReportHandler findReportHandler(ReportHandlerType reportHandlerType) {
        return reportHandlers
                .stream()
                .filter(reportHandler -> reportHandlerType == reportHandler.getReportHandlerType())
                .findFirst()
                .orElseThrow(
                        () ->
                                new UnsupportedOperationException(
                                        "Report handler not implemented for type: " + reportHandlerType.name()
                                )
                );
    }
}
