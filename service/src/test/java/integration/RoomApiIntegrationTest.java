package integration;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestConstants.DEFAULT_ROOM_ID;
import static utils.TestDataCreator.newCreateRoomDTO;
import static utils.TestDataCreator.newRoomBuilder;

import br.com.sw2you.realmeet.api.facade.RoomApi;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import core.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoomApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private RoomApi roomApi;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(roomApi.getApiClient());
    }

    @Test
    void testGetRoomSuccess() {
        var room = newRoomBuilder().build();
        roomRepository.saveAndFlush(room);

        assertNotNull(room.getId());
        Assertions.assertTrue(room.getActive());

        var dto = roomApi.getRoom(room.getId());
        assertEquals(room.getId(), dto.getId());
        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
    }

    @Test
    void testGetRoomInactive() {
        var room = newRoomBuilder().active(false).build();
        roomRepository.saveAndFlush(room);
        //test se a rom e false
        assertFalse(room.getActive());
        // se tem exception de id
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.getRoom(room.getId()));
    }

    @Test
    void testGetRoomDoesNotExists() {
        //nao insere nada no banco
        //se tem exception de id que nao
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.getRoom(DEFAULT_ROOM_ID));
    }

    @Test
    void testCreateRoomSuccess() {
        var createRoomDTO = newCreateRoomDTO();
        var romDTO = roomApi.createRoom(createRoomDTO);
        assertEquals(createRoomDTO.getName(), romDTO.getName());
        assertEquals(createRoomDTO.getSeats(), romDTO.getSeats());
        assertNotNull(romDTO.getId()); //verificar se existe o id após o cadastro

        //verificar se os dados do banco estão ok, conforme cadastrados
        var room = roomRepository.findById(romDTO.getId()).orElseThrow();//.orElseThrow() se não encontrar lança uma exception
        assertEquals(romDTO.getName(), room.getName());
        assertEquals(romDTO.getSeats(), room.getSeats());
    }

    @Test
    void testCreateRoomValidationError() {
        assertThrows(HttpClientErrorException.class, () -> roomApi.createRoom(newCreateRoomDTO().name(null)));
    }
}
