package br.com.sw2you.realmeet;

import br.com.sw2you.realmeet.api.facade.RoomsApi;
import br.com.sw2you.realmeet.api.model.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
public class RoomController implements RoomsApi {

    @Override
    public CompletableFuture<ResponseEntity<Room>> listRooms(@Valid Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(new Room().id(1l).name("Room 1")));
    }
}
