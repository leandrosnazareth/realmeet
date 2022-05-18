package utils;


import br.com.sw2you.realmeet.util.DateUtils;
import java.time.OffsetDateTime;

public final class TestConstants {
    public static final long DEFAULT_ROOM_ID = 1L;
    public static final String DEFAULT_ROOM_NAME = "Room A";
    public static final int DEFAULT_ROOM_SEATS = 6;
    public static final long DEFAULT_ALLOCATION_ID = 1L;
    public static final String DEFAULT_ALLOCATION_SUBJECT = "Some Subject";
    public static final String DEFAULT_EMPLOYEE_NAME = "John Doe";
    public static final String DEFAULT_EMPLOYEE_EMAIL = "john@email.com";
    public static final OffsetDateTime DEFAULT_ALLOCATION_START_AT = DateUtils.now().plusDays(1);
    public static final OffsetDateTime DEFAULT_ALLOCATION_END_AT = DEFAULT_ALLOCATION_START_AT.plusHours(1);
    public static final String TEST_CLIENT_API_KEY = "test-api-key";
    public static final String TEST_CLIENT_DESCRIPTION = "Some Test Client";
    public static final String EMAIL_TO = "a@a.com";

    private TestConstants() {}
}
