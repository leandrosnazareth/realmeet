package utils;

import br.com.sw2you.realmeet.mapper.AllocationMapper;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import org.mapstruct.factory.Mappers;

public final class MapperUtils {

    private MapperUtils() {}

    //fornecer a instancia para os tests
    public static RoomMapper roomMapper() {
        return Mappers.getMapper(RoomMapper.class);
    }

    //fornecer a instancia para os tests
    public static AllocationMapper allocationMapper() {
        return Mappers.getMapper(AllocationMapper.class);
    }

}
