package br.com.sw2you.realmeet.service;

import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.mapper.AllocationMapper;
import org.springframework.stereotype.Service;

@Service
public class AllocationService {
    private final AllocationRepository allocationRepository;
    private final AllocationMapper allocationMapper;
//    private final AllocationValidator allocationValidator;


    public AllocationService(AllocationRepository allocationRepository, AllocationMapper allocationMapper) {
        this.allocationRepository = allocationRepository;
        this.allocationMapper = allocationMapper;
//        this.allocationValidator = allocationValidator;
    }

    public AllocationDTO createAllocation(CreateAllocationDTO createAllocationDTO) {
//        //validar os dados
//        allocationValidator.validate(createAllocationDTO);
//        //transformer allocationDTO em rom
//        var allocation = allocationMapper.fromCreateAllocationDtoToEntity(createAllocationDTO);
//        allocationRepository.save(allocation);
//        //transformar entity allocation em allocationdto para retornar
//        return allocationMapper.fromEntityToDto(allocation);
        return null;
    }
}
