package br.com.sw2you.realmeet.controller;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import br.com.sw2you.realmeet.api.facade.AllocationsApi;
import br.com.sw2you.realmeet.api.model.*;
import br.com.sw2you.realmeet.service.AllocationService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

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

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteAllocation(Long id) {
        return runAsync(() -> allocationService.deleteAllocation(id), controllersExecutor)
                .thenApply(ResponseEntityUtils::noContent);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> updateAllocation(Long id, UpdateAllocationDTO updateAllocationDTO) {
        return runAsync(() -> allocationService.updateAllocation(id, updateAllocationDTO), controllersExecutor)
                .thenApply(ResponseEntityUtils::noContent);
    }
}
