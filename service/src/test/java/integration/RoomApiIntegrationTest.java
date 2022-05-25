package integration;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestConstants.DEFAULT_ROOM_ID;
import static utils.TestConstants.TEST_CLIENT_API_KEY;
import static utils.TestDataCreator.newCreateRoomDTO;
import static utils.TestDataCreator.newRoomBuilder;

import br.com.sw2you.realmeet.api.facade.RoomApi;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
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
        setLocalHostBasePath(roomApi.getApiClient(), "/v1");
    }

    @Test
    void testGetRoomSuccess() {
        var room = newRoomBuilder().build();
        roomRepository.saveAndFlush(room);

        assertNotNull(room.getId());
        Assertions.assertTrue(room.getActive());

        var dto = roomApi.getRoom(TEST_CLIENT_API_KEY, room.getId());
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
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.getRoom(TEST_CLIENT_API_KEY, room.getId()));
    }

    @Test
    void testGetRoomDoesNotExists() {
        //nao insere nada no banco
        //se tem exception de id que nao
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.getRoom(TEST_CLIENT_API_KEY, DEFAULT_ROOM_ID));
    }

    @Test
    void testCreateRoomSuccess() {
        var createRoomDTO = newCreateRoomDTO();
        var romDTO = roomApi.createRoom(TEST_CLIENT_API_KEY, createRoomDTO);
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
        assertThrows(HttpClientErrorException.class, () -> roomApi.createRoom(TEST_CLIENT_API_KEY, (CreateRoomDTO) newCreateRoomDTO().name(null)));
    }

    @Test
    public void testDeleteRoomSuccess() {
        //salvar uma room para deletar
        var roomId = roomRepository.saveAndFlush(newRoomBuilder().build()).getId();
        //deletar o id
        roomApi.deleteRoom(TEST_CLIENT_API_KEY, roomId);
        //buscar o id e verificar se ele está com status inativo
        assertFalse(roomRepository.findById(roomId).orElseThrow().getActive());
    }

    @Test // teste deletar uma room que não existe
    public void testDeleteRoomDoesNotExist() {
        //faço um assert com um código que eu sei que tem no banco
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.deleteRoom(TEST_CLIENT_API_KEY, 1L));
    }

    @Test
    void testUpdateRoomSuccess() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        //criar um dto diferente para atualizar, concatenar com A para ficar diferente
        var updateRoomDTO = new UpdateRoomDTO().name(room.getName() + "A").seats(room.getSeats() + 1);
        //atualiza a rom
        roomApi.updateRoom(TEST_CLIENT_API_KEY, room.getId(), updateRoomDTO);

        var updatedRoom = roomRepository.findById(room.getId()).orElseThrow();
        assertEquals(updateRoomDTO.getName(), updatedRoom.getName());
        assertEquals(updateRoomDTO.getSeats(), updatedRoom.getSeats());
    }


    @Test
    void testUpdateRoomDoesNotExist() {
        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> roomApi.updateRoom(TEST_CLIENT_API_KEY, 1L, new UpdateRoomDTO().name("Room").seats(10))
        );
    }

    @Test
    void testUpdateRoomValidationError() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        assertThrows(
                HttpClientErrorException.UnprocessableEntity.class,
                () -> roomApi.updateRoom(TEST_CLIENT_API_KEY, room.getId(), new UpdateRoomDTO().name(null).seats(10))//name null não existe no banco
        );
    }
}
