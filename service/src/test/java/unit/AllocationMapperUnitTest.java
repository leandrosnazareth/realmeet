package unit;

import static utils.MapperUtils.allocationMapper;
import static utils.TestDataCreator.*;

import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.mapper.AllocationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AllocationMapperUnitTest {
    private AllocationMapper victim;

    @BeforeEach
    void setupEach() {
        victim = allocationMapper();
    }

    @Test
    void testCreateAllocationDtoToEntity() {
        var createAllocationDTO = newCreateAllocationDTO();
        var allocation = victim.fromCreateAllocationDTOToEntity(createAllocationDTO, Room.newBuilder().build());

        Assertions.assertEquals(createAllocationDTO.getSubject(), allocation.getSubject());
        Assertions.assertNull(allocation.getRoom().getId());
        Assertions.assertEquals(createAllocationDTO.getEmployeeName(), allocation.getEmployee().getName());
        Assertions.assertEquals(createAllocationDTO.getEmployeeEmail(), allocation.getEmployee().getEmail());
        Assertions.assertEquals(createAllocationDTO.getStartAt(), allocation.getStartAt());
        Assertions.assertEquals(createAllocationDTO.getEndAt(), allocation.getEndAt());
    }

    @Test
    void testFromEntityToAllocationDTO() {
        var allocation = newAllocationBuilder(newRoomBuilder().id(1L).build()).build();
        var allocationDTO = victim.fromEntityToAllocationDTO(allocation);

        Assertions.assertEquals(allocation.getSubject(), allocationDTO.getSubject());
        Assertions.assertEquals(allocation.getId(), allocationDTO.getId());
        Assertions.assertEquals(allocation.getEmployee().getName(), allocationDTO.getEmployeeName());
        Assertions.assertEquals(allocation.getEmployee().getEmail(), allocationDTO.getEmployeeEmail());
        Assertions.assertEquals(allocation.getStartAt(), allocationDTO.getStartAt());
        Assertions.assertEquals(allocation.getEndAt(), allocationDTO.getEndAt());
    }
}