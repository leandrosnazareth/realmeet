package unit;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.validator.RoomValidator;
import br.com.sw2you.realmeet.validator.ValidationError;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.TestConstants;
import utils.TestDataCreator;

@ExtendWith(MockitoExtension.class)
public class RoomValidatorUnitTest {

    private RoomValidator victim; //vítima

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setupEach() {
        victim = new RoomValidator(roomRepository);
    }

    @Test
    public void testValidarSala() {
        victim.validate(TestDataCreator.newCreateRoomDTO());
    }

    @Test
    public void testNomeDaSalaPreenchido() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(TestDataCreator.newCreateRoomDTO().name(null)));
        //verificar se existe somente um erro na lista
        assertEquals(1, exception.getValidationErrors().getNumberOfErros());
        //verificar se o teste do índice 0, ou seja, o primeiro, deu certo. No caso se deu errado e coincide com o erro MISSING
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + MISSING), exception.getValidationErrors().getError(0));
    }

    @Test
    public void testTamanhoMaxNomeRoom() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(TestDataCreator.newCreateRoomDTO().name(
                //completa uma palavra com a quantidade máxima de caracteres + 1 com letras A
                StringUtils.rightPad("X", ROOM_NAME_MAX_LENGTH + 1, "A"))));
        //verificar se existe somente um erro na lista
        assertEquals(1, exception.getValidationErrors().getNumberOfErros());
        //verificar se o teste do índice 0, ou seja, o primeiro, deu certo. No caso se deu errado e coincide com o erro EXCEEDS_MAX_LENGTH
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + EXCEEDS_MAX_LENGTH), exception.getValidationErrors().getError(0));
    }

    @Test
    public void testQuantidadeAssentosPreenchido() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(TestDataCreator.newCreateRoomDTO().seats(null)));
        //verificar se existe somente um erro na lista
        assertEquals(1, exception.getValidationErrors().getNumberOfErros());
        //verificar se o teste do índice 0, ou seja, o primeiro, deu certo. No caso se deu errado e coincide com o erro MISSING
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + MISSING), exception.getValidationErrors().getError(0));
    }

    @Test
    public void testValorAbaixoDoMin() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(TestDataCreator.newCreateRoomDTO().seats(ROOM_SEATS_MIN_VALUE - 1))); //1-1=0
        //verificar se existe somente um erro na lista
        assertEquals(1, exception.getValidationErrors().getNumberOfErros());
        //verificar se o teste do índice 0, ou seja, o primeiro, deu certo. No caso se deu errado e coincide com o erro BELOW_MIN_VALUE
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + BELOW_MIN_VALUE), exception.getValidationErrors().getError(0));
    }

    @Test
    public void testValorAbaixoDoMax() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(TestDataCreator.newCreateRoomDTO().seats(ROOM_SEATS_MAX_VALUE + 1))); //1-1=0
        //verificar se existe somente um erro na lista
        assertEquals(1, exception.getValidationErrors().getNumberOfErros());
        //verificar se o teste do índice 0, ou seja, o primeiro, deu certo. No caso se deu errado e coincide com o erro BELOW_MIN_VALUE
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + EXCEEDS_MAX_VALUE), exception.getValidationErrors().getError(0));
    }


    @Test
    public void testVerificarDuplicidadeNomeRoom() {
        BDDMockito.given(roomRepository.findByNameAndActive(TestConstants.DEFAULT_ROOM_NAME, true))
                .willReturn(Optional.of(TestDataCreator.newRoomBuilder().build()));
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(TestDataCreator.newCreateRoomDTO()));

        //verificar se existe somente um erro na lista
        assertEquals(1, exception.getValidationErrors().getNumberOfErros());
        //verificar se o teste do índice 0, ou seja, o primeiro, deu certo. No caso se deu errado e coincide com o erro DUPLICATE
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + DUPLICATE), exception.getValidationErrors().getError(0));
    }
}
