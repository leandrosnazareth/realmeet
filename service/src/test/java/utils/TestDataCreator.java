package utils;


import static utils.TestConstants.DEFAULT_ROOM_NAME;
import static utils.TestConstants.DEFAULT_ROOM_SEATS;

import br.com.sw2you.realmeet.domain.entity.Room;

public final class TestDataCreator {

    private TestDataCreator() {}

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }
}
