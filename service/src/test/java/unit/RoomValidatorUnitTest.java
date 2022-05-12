package unit;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.MISSING;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.ROOM_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.validator.RoomValidator;
import br.com.sw2you.realmeet.validator.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.TestDataCreator;

@ExtendWith(MockitoExtension.class)
public class RoomValidatorUnitTest {

    private RoomValidator victim; //vítima

    @BeforeEach
    void setupEach() {
        victim = new RoomValidator();
    }

    //validar sala
    @Test
    public void testValidateWhenRoomIsValid() {
        victim.validate(TestDataCreator.newCreateRoomDTO());
    }

    // validar se o nome da sala está presente
    @Test
    public void testValidateWhenNameIsMissing() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(TestDataCreator.newCreateRoomDTO().name(null)));
        //verificar se existe somente um erro na lista
        assertEquals(1, exception.getValidationErrors().getNumberOfErros());
        //verificar se o teste do índice 0, ou seja, o primeiro, deu certo
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + MISSING), exception.getValidationErrors().getError(0));
    }

}
