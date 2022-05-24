package br.com.sw2you.realmeet.controller;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import br.com.sw2you.realmeet.api.facade.AllocationsApi;
import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.UpdateAllocationDTO;
import br.com.sw2you.realmeet.service.AllocationService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import java.time.LocalDate;
import java.util.List;
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

    @Override
    public CompletableFuture<ResponseEntity<List<AllocationDTO>>> listAllocations(String employeeEmail, Long roomId, LocalDate startAt, LocalDate endAt, String orderBy, Integer limit, Integer page) {
        //return supplyAsync
        return supplyAsync(() -> allocationService.listAllocations(employeeEmail, roomId, startAt, endAt, orderBy, limit, page), controllersExecutor)
                .thenApply(ResponseEntityUtils::ok);
    }
}
