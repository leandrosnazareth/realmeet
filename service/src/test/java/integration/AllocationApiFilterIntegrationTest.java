package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.TestConstants.*;
import static utils.TestDataCreator.*;

import br.com.sw2you.realmeet.api.facade.AllocationApi;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.service.AllocationService;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import core.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

class AllocationApiFilterIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private AllocationApi api;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private AllocationService allocationService;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void testFilterAllAllocations() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var allocation1 = allocationRepository.saveAndFlush(
                newAllocationBuilder(room).subject(DEFAULT_ALLOCATION_SUBJECT + 1).build()
        );
        var allocation2 = allocationRepository.saveAndFlush(
                newAllocationBuilder(room).subject(DEFAULT_ALLOCATION_SUBJECT + 2).build()
        );
        var allocation3 = allocationRepository.saveAndFlush(
                newAllocationBuilder(room).subject(DEFAULT_ALLOCATION_SUBJECT + 3).build()
        );

        var allocationDTOList = api.listAllocations(null, null, null, null, null, null, null);

        assertEquals(3, allocationDTOList.size());
        assertEquals(allocation1.getSubject(), allocationDTOList.get(0).getSubject());
        assertEquals(allocation2.getSubject(), allocationDTOList.get(1).getSubject());
        assertEquals(allocation3.getSubject(), allocationDTOList.get(2).getSubject());
    }

    @Test
    void testFilterAllocationsByRoomId() {
        var roomA = roomRepository.saveAndFlush(newRoomBuilder().name(DEFAULT_ROOM_NAME + "A").build());
        var roomB = roomRepository.saveAndFlush(newRoomBuilder().name(DEFAULT_ROOM_NAME + "B").build());

        var allocation1 = allocationRepository.saveAndFlush(newAllocationBuilder(roomA).build());
        var allocation2 = allocationRepository.saveAndFlush(newAllocationBuilder(roomA).build());
        allocationRepository.saveAndFlush(newAllocationBuilder(roomB).build());

        var allocationDTOList = api.listAllocations(
                null,
                roomA.getId(),
                null,
                null,
                null,
                null,
                null
        );

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }

    @Test
    void testFilterAllocationsByEmployeeEmail() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var employee1 = newEmployeeBuilder().email(DEFAULT_EMPLOYEE_EMAIL + 1).build();
        var employee2 = newEmployeeBuilder().email(DEFAULT_EMPLOYEE_EMAIL + 2).build();

        var allocation1 = allocationRepository.saveAndFlush(newAllocationBuilder(room).employee(employee1).build());
        var allocation2 = allocationRepository.saveAndFlush(newAllocationBuilder(room).employee(employee1).build());
        allocationRepository.saveAndFlush(newAllocationBuilder(room).employee(employee2).build());

        var allocationDTOList = api.listAllocations(
                employee1.getEmail(),
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }

    @Test
    void testFilterAllocationsByDateRange() {
        var baseStartAt = OffsetDateTime.now().plusDays(2).withHour(14).withMinute(0);
        var baseEndAt = OffsetDateTime.now().plusDays(4).withHour(20).withMinute(0);

        var room = roomRepository.saveAndFlush(newRoomBuilder().build());

        var allocation1 = allocationRepository.saveAndFlush(
                newAllocationBuilder(room).startAt(baseStartAt.plusHours(1)).endAt(baseStartAt.plusHours(2)).build()
        );
        var allocation2 = allocationRepository.saveAndFlush(
                newAllocationBuilder(room).startAt(baseStartAt.plusHours(4)).endAt(baseStartAt.plusHours(5)).build()
        );

        allocationRepository.saveAndFlush(
                newAllocationBuilder(room).startAt(baseEndAt.plusDays(1)).endAt(baseEndAt.plusDays(3).plusHours(1)).build()
        );

        var allocationDTOList = api.listAllocations(
                null,
                null,
                baseStartAt.toLocalDate(),
                baseEndAt.toLocalDate(),
                null,
                null,
                null
        );

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }

    @Test
    void testFilterAllocationUsingPagination() {
        persistAllocations(15);
        ReflectionTestUtils.setField(allocationService, "maxLimit", 10);

        var allocationListPage1 = api.listAllocations(null, null, null, null, null, null, 0);
        assertEquals(10, allocationListPage1.size());

        var allocationListPage2 = api.listAllocations(null, null, null, null, null, null, 1);
        assertEquals(5, allocationListPage2.size());
    }

    @Test
    void testFilterAllocationUsingPaginationAndLimit() {
        persistAllocations(25);
        ReflectionTestUtils.setField(allocationService, "maxLimit", 50);

        var allocationListPage1 = api.listAllocations(null, null, null, null, null, 10, 0);
        assertEquals(10, allocationListPage1.size());

        var allocationListPage2 = api.listAllocations(null, null, null, null, null, 10, 1);
        assertEquals(10, allocationListPage2.size());

        var allocationListPage3 = api.listAllocations(null, null, null, null, null, 10, 2);
        assertEquals(5, allocationListPage3.size());
    }

    @Test
    void testFilterAllocationOrderByStartAtDesc() {
        var allocationList = persistAllocations(3);
        var allocationDTOList = api.listAllocations(
                null,
                null,
                null,
                null,
                "-startAt",
                null,
                null
        );

        assertEquals(3, allocationDTOList.size());
        assertEquals(allocationList.get(0).getId(), allocationDTOList.get(2).getId());
        assertEquals(allocationList.get(1).getId(), allocationDTOList.get(1).getId());
        assertEquals(allocationList.get(2).getId(), allocationDTOList.get(0).getId());
    }

    @Test
    void testFilterAllocationOrderByInvalidField() {
        assertThrows(
                HttpClientErrorException.UnprocessableEntity.class,
                () -> api.listAllocations(null, null, null, null, "invalid", null, null)
        );
    }

    //criar uma lista de allocations
    private List<Allocation> persistAllocations(int numberOfAllocations) {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());

        return IntStream
                .range(0, numberOfAllocations)
                .mapToObj(
                        i ->
                                allocationRepository.saveAndFlush(
                                        newAllocationBuilder(room)
                                                .subject(DEFAULT_ALLOCATION_SUBJECT + "_" + (i + 1))
                                                .startAt(DEFAULT_ALLOCATION_START_AT.plusHours(i + 1))
                                                .endAt(DEFAULT_ALLOCATION_END_AT.plusHours(i + 1))
                                                .build()
                                )
                )
                .collect(Collectors.toList());
    }
}

