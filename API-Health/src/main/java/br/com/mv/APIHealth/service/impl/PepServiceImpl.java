package br.com.mv.APIHealth.service.impl;


import br.com.mv.APIHealth.domain.entity.Pep;
import br.com.mv.APIHealth.domain.entity.PepLog;
import br.com.mv.APIHealth.domain.repository.PepLogRepository;
import br.com.mv.APIHealth.domain.repository.PepRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;

import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.service.PepService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class PepServiceImpl implements PepService {


    private final PepRepository pepRepository;
    private final PepLogRepository pepLogRepository;

    @Override
    public PepDTO create(PepDTO pepDTO) {
        Pep pep = new Pep();

        BeanUtils.copyProperties(pepDTO,pep);
        pep.setCreatedAt(LocalDateTime.now());
        Pep newPep = this.pepRepository.save(pep);
        PepLog pepLog = new PepLog();
        pepLog.setPepId(pep.getId());
        pepLog.setAction("pep was created");
        pepLog.setCreatedAt(LocalDateTime.now());
        this.pepLogRepository.save(pepLog);
        BeanUtils.copyProperties(newPep,pepDTO);
        return pepDTO;
    }

    @Override
    public PepDTO getPepById(UUID id) {
        Pep pep = this.validateExistPep(id);
        PepDTO pepDTO = new PepDTO();
        BeanUtils.copyProperties(pep,pepDTO);
        return pepDTO;
    }

    @Override
    public PepDTO updateById(UUID id, PepDTO pepDTO) {
        Pep pep = this.validateExistPep(id);
        this.validateForUpdatePep(pepDTO,pep);
        BeanUtils.copyProperties(pepDTO,pep);
        pep.setId(id);
        pep.setUpdateAt(LocalDateTime.now());
        Pep pepUpdated = this.pepRepository.save(pep);
        PepLog pepLog = new PepLog();
        pepLog.setPepId(pep.getId());
        pepLog.setAction("pep was updated");
        pepLog.setCreatedAt(LocalDateTime.now());
        this.pepLogRepository.save(pepLog);
        BeanUtils.copyProperties(pepUpdated,pepDTO);
        return pepDTO;
    }

    @Override
    public List<PepDTO> getAll() {
        List<Pep> peps= this.pepRepository.findAll();
        List<PepDTO> pepDTOS = new ArrayList();
        peps.forEach(pep -> {
            PepDTO pepDTO = new PepDTO();
            BeanUtils.copyProperties(pep,pepDTO);
            pepDTOS.add(pepDTO);
        });
        return pepDTOS;
    }

    @Override
    public void deleteById(UUID id) {
        this.validateExistPep(id);
        this.pepRepository.deleteById(id);
    }

    @Override
    public void updateDatePep(LocalDateTime date) {

    }
    private Pep validateExistPep(UUID pepId){
        return this.pepRepository.findById(pepId).orElseThrow(() -> new ResourceNotFoundException("Pep não encontrado."));
    }
    private void validateForUpdatePep(PepDTO pepDTO, Pep pep){
       // if(pepDTO.getPatientId() != null){
       //     pep.setPatientId(pepDTO.getPatientId());
       // }
        if(pepDTO.getPepNumber() != null){
            pep.setPepNumber(pepDTO.getPepNumber());
        }
        if(pepDTO.getStatus() != null){
            pep.setStatus(pepDTO.getStatus());
        }
        if(pepDTO.getCreatedAt() != null){
            pep.setCreatedAt(pepDTO.getCreatedAt());
        }
        if(pepDTO.getAllergies() != null){
            pep.setAllergies(pepDTO.getAllergies());
        }
        if(pepDTO.getBoodType() != null){
            pep.setBloodType(pepDTO.getBoodType());
        }
        //if(pepDTO.getDoctorId() != null){
        //    pep.setDoctorId(pepDTO.getDoctorId());
        //}
        if(pepDTO.getPrescription() != null){
            pep.setPrescription(pepDTO.getPrescription());
        }
        if(pepDTO.getUpdateAt() != null){
            pep.setUpdateAt(pepDTO.getUpdateAt());
        }
    }
}
