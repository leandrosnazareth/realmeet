package integration;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestConstants.*;
import static utils.TestDataCreator.*;

import br.com.sw2you.realmeet.api.facade.AllocationApi;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import core.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import utils.TestDataCreator;

class AllocationApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private AllocationApi allocationApi;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private AllocationRepository allocationRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(allocationApi.getApiClient(), "/v1");
    }

    @Test
    void testCreateAllocationSuccess() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDTO = TestDataCreator.newCreateAllocationDTO().roomId(room.getId());
        var allocationDTO = allocationApi.createAllocation(TEST_CLIENT_API_KEY, createAllocationDTO);

        assertNotNull(allocationDTO.getId());
        assertEquals(room.getId(), allocationDTO.getRoomId());
        assertEquals(createAllocationDTO.getSubject(), allocationDTO.getSubject());
        assertEquals(createAllocationDTO.getEmployeeName(), allocationDTO.getEmployeeName());
        assertEquals(createAllocationDTO.getEmployeeEmail(), allocationDTO.getEmployeeEmail());
        assertTrue(createAllocationDTO.getStartAt().isEqual(allocationDTO.getStartAt()));
        assertTrue(createAllocationDTO.getEndAt().isEqual(allocationDTO.getEndAt()));
    }

    @Test
    void testCreateAllocationValidationError() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDTO = newCreateAllocationDTO().roomId(room.getId()).subject(null);

        assertThrows(
                HttpClientErrorException.UnprocessableEntity.class,
                () -> allocationApi.createAllocation(TEST_CLIENT_API_KEY, createAllocationDTO)
        );
    }

    @Test
    void testCreateAllocationWhenRoomDoesNotExist() {
        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> allocationApi.createAllocation(TEST_CLIENT_API_KEY, newCreateAllocationDTO())
        );
    }

    @Test
    void testDeleteAllocationSuccess() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var allocation = allocationRepository.saveAndFlush(newAllocationBuilder(room).build());

        allocationApi.deleteAllocation(TEST_CLIENT_API_KEY, allocation.getId());
        assertFalse(allocationRepository.findById(allocation.getId()).isPresent());
    }

    @Test
    void testDeleteAllocationInThePast() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var allocation = allocationRepository.saveAndFlush(
                newAllocationBuilder(room).startAt(OffsetDateTime.now().minusDays(1)).endAt(OffsetDateTime.now().minusDays(1).plusHours(1)).build()
        );

        assertThrows(
                HttpClientErrorException.UnprocessableEntity.class,
                () -> allocationApi.deleteAllocation(TEST_CLIENT_API_KEY, allocation.getId())
        );
    }

    @Test
    void testDeleteAllocationDoesNotExist() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> allocationApi.deleteAllocation(TEST_CLIENT_API_KEY, 1L));
    }

    @Test
    void testUpdateAllocationSuccess() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDTO = newCreateAllocationDTO().roomId(room.getId());
        var allocationDTO = allocationApi.createAllocation(TEST_CLIENT_API_KEY, createAllocationDTO);

        var updateAllocationDTO = newUpdateAllocationDTO()
                .subject(DEFAULT_ALLOCATION_SUBJECT + "_")
                .startAt(DEFAULT_ALLOCATION_START_AT.plusDays(1))
                .endAt(DEFAULT_ALLOCATION_END_AT.plusDays(1));

        allocationApi.updateAllocation(TEST_CLIENT_API_KEY, allocationDTO.getId(), updateAllocationDTO);

        var allocation = allocationRepository.findById(allocationDTO.getId()).orElseThrow();

        assertEquals(updateAllocationDTO.getSubject(), allocation.getSubject());
        assertTrue(updateAllocationDTO.getStartAt().isEqual(allocation.getStartAt()));
        assertTrue(updateAllocationDTO.getEndAt().isEqual(allocation.getEndAt()));
    }

    @Test
    void testUpdateAllocationDoesNotExist() {
        assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> allocationApi.updateAllocation(TEST_CLIENT_API_KEY, 1L, newUpdateAllocationDTO())
        );
    }

    @Test
    void testUpdateAllocationValidationError() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDTO = newCreateAllocationDTO().roomId(room.getId());
        var allocationDTO = allocationApi.createAllocation(TEST_CLIENT_API_KEY, createAllocationDTO);

        assertThrows(
                HttpClientErrorException.UnprocessableEntity.class,
                () ->
                        allocationApi.updateAllocation(TEST_CLIENT_API_KEY, allocationDTO.getId(), newUpdateAllocationDTO().subject(null))
        );
    }
}
