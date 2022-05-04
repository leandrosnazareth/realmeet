package integration;

import static org.junit.Assert.*;
import static utils.TestConstants.DEFAULT_ROOM_ID;
import static utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.api.facade.RoomApi;
import core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.bouncycastle.jcajce.provider.drbg.DRBG;

class RoomApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private RoomApi roomApi;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(roomApi.getApiClient(), "/v1");
    }

    @Test
    void testGetRoomSuccess() {
        var room = newRoomBuilder().build();
        roomRepository.saveAndFlush(room);

        assertNotNull(room.getId());//verificar se o id não ta nulo
        assertTrue(room.getActive());//garantir que o getactive é verdadeiro

        var dto = roomApi.getRoom(room.getId());
        assertEquals(room.getId(), dto.getId());
        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
    }

    @Test
    void testGetRoomInactive() {
        var room = newRoomBuilder().active(false).build();
        roomRepository.saveAndFlush(room);
        //teste se a rom é false
        assertFalse(room.getActive());
        // verificar se tem exception de id que não existe
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.getRoom(room.getId()));
    }

    @Test
    void testGetRoomDoesNotExists() {
        //não inseri nada no banco
        //verificar se tem exception de id que não existe
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.getRoom(DEFAULT_ROOM_ID));
    }
}
