package br.com.sw2you.realmeet.mapper;


import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AllocationMapper {

    //recebe um allocation dto e transforma em allocation
    @Mapping(source = "room", target = "room")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "createAllocationDTO.employeeName", target = "employee.name")
    @Mapping(source = "createAllocationDTO.employeeEmail", target = "employee.email")
    public abstract Allocation fromCreateAllocationDTOToEntity(CreateAllocationDTO createAllocationDTO, Room room);

    //recebe um allocation e transforma em allocationdto
    @Mapping(source = "employee.name", target = "employeeName")
    @Mapping(source = "employee.email", target = "employeeEmail")
    @Mapping(source = "room.id", target = "roomId")
    public abstract AllocationDTO fromEntityToAllocationDTO(Allocation allocation);
}