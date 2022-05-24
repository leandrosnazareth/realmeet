package br.com.sw2you.realmeet.util;

public final class Constants {
    public static final String ALLOCATIONS_MAX_FILTER_LIMIT = "${realmeet.allocations.maxFilterLimit:50}";
    public static final String ALLOCATION_REPORT_MAX_MONTHS_INTERVAL =
            "${realmeet.reports.allocationReport.maxMonthsInterval:12}";
    public static final String ALLOCATION = "allocation";
    public static final String EMPTY = "";
    public static final String REPORT = "report_";

    private Constants() {
    }
}