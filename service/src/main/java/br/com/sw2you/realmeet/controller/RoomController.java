package br.com.sw2you.realmeet.controller;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import br.com.sw2you.realmeet.api.facade.RoomsApi;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController implements RoomsApi { //RoomApi gerado pelo swagger
    private final Executor controllersExecutor;
    private final RoomService roomService;

    public RoomController(Executor controllersExecutor, RoomService roomService) {
        this.controllersExecutor = controllersExecutor;
        this.roomService = roomService;
    }

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> getRoom(Long id) {
        //return supplyAsync
        return supplyAsync(() -> roomService.getRoom(id), controllersExecutor).thenApply(ResponseEntityUtils::ok);
    }

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> createRoom(CreateRoomDTO createRoomDTO) {
        //return supplyAsync
        return supplyAsync(() -> roomService.createRoom(createRoomDTO), controllersExecutor).thenApply(ResponseEntityUtils::created);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteRoom(Long id) {
        //runAsync não retorna nada
        return runAsync(() -> roomService.deleteRoom(id), controllersExecutor).thenApply(ResponseEntityUtils::noContent);//noContent foi deletado
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> updateRoom(Long id, UpdateRoomDTO updateRoomDTO) {
        //runAsync não retorna nada
        return runAsync(() -> roomService.updateRoom(id, updateRoomDTO), controllersExecutor).thenApply(ResponseEntityUtils::noContent);
    }
}
