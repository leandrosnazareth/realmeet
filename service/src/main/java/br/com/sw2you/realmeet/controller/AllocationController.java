package br.com.sw2you.realmeet.controller;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import br.com.sw2you.realmeet.api.facade.AllocationsApi;
import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.service.AllocationService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllocationController implements AllocationsApi { //RoomApi gerado pelo swagger
    private final Executor controllersExecutor;
    private final AllocationService allocationService;

    public AllocationController(Executor controllersExecutor, AllocationService allocationService) {
        this.controllersExecutor = controllersExecutor;
        this.allocationService = allocationService;
    }

    @Override
    public CompletableFuture<ResponseEntity<AllocationDTO>> createAllocation(CreateAllocationDTO createAllocationDTO) {
        //return supplyAsync
        return supplyAsync(() -> allocationService.createAllocation(createAllocationDTO), controllersExecutor)
                .thenApply(ResponseEntityUtils::created);
    }
}
