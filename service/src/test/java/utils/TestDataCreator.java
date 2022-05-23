package utils;


import static utils.TestConstants.*;

import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateAllocationDTO;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.model.Employee;

public final class TestDataCreator {

    private TestDataCreator() {
    }

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }

    //crear o Allocation padrão para testes
    public static Allocation.Builder newAllocationBuilder(Room room) {
        return Allocation
                .newBuilder()
                .subject(DEFAULT_ALLOCATION_SUBJECT)
                .room(room)
                .employee(Employee.newBuilder().name(DEFAULT_EMPLOYEE_NAME).email(DEFAULT_EMPLOYEE_EMAIL).build())
                .startAt(DEFAULT_ALLOCATION_START_AT)
                .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    //crear o roomdto padrão para testes
    public static CreateRoomDTO newCreateRoomDTO() {
        return (CreateRoomDTO) new CreateRoomDTO().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }

    //crear o allocationdto padrão para testes
    public static CreateAllocationDTO newCreateAllocationDTO() {
        return (CreateAllocationDTO) new CreateAllocationDTO()
                .subject(DEFAULT_ALLOCATION_SUBJECT)
                .roomId(DEFAULT_ROOM_ID)
                .employeeName(DEFAULT_EMPLOYEE_NAME)
                .employeeEmail(DEFAULT_EMPLOYEE_EMAIL)
                .startAt(DEFAULT_ALLOCATION_START_AT)
                .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    //crear o updateallocationdto padrão para testes
    public static UpdateAllocationDTO newUpdateAllocationDTO() {
        return new UpdateAllocationDTO()
                .subject(DEFAULT_ALLOCATION_SUBJECT)
                .startAt(DEFAULT_ALLOCATION_START_AT)
                .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    public static Employee.Builder newEmployeeBuilder() {
        return Employee.newBuilder().name(DEFAULT_EMPLOYEE_NAME).email(DEFAULT_EMPLOYEE_EMAIL);
    }
}
