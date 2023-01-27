package br.com.mv.APIHealth.service.impl;


import br.com.mv.APIHealth.domain.entity.Pep;
import br.com.mv.APIHealth.domain.enums.EStatePatient;
import br.com.mv.APIHealth.domain.repository.PepRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.GetPepDTO;
import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.PepLogDTO;
import br.com.mv.APIHealth.rest.dto.PutPepDTO;
import br.com.mv.APIHealth.service.PepService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PepServiceImpl implements PepService {


    private final PepRepository pepRepository;
    private final PepLogServiceImpl pepLogService;
    private final MessageSource messageSource;

    private final String MESSAGECREATED = "pep was created";
    private final String MESSAGEUPDATED = "pep was updated";
    private final String MESSAGEDELETED = "pep was deleted";

    @Override
    public GetPepDTO create(PepDTO pepDTO) {
        this.validateExistPepNumber( pepDTO.getPepNumber());
        Pep pep = new Pep();
        pepDTO.setPepNumber( (int) Math.floor(Math.random()*(999999999 +1)+0));

        BeanUtils.copyProperties(pepDTO,pep);
        pep.setCreatedAt(LocalDateTime.now());
        pep.setUpdateAt(LocalDateTime.now());
        pep.setStatus(EStatePatient.CONSULTATION);

        Pep newPep = this.pepRepository.save(pep);

        this.createLog(pep.getId(),MESSAGECREATED);

        BeanUtils.copyProperties(newPep,pepDTO);

        return  new GetPepDTO(pepDTO);
    }

    @Override
    public GetPepDTO getPepById(UUID id) {

        Pep pep = this.validateExistPep(id);

        PepDTO pepDTO = new PepDTO();

        BeanUtils.copyProperties(pep,pepDTO);

        return new GetPepDTO(pepDTO);
    }

    @Override
    public GetPepDTO updateById(UUID id, PutPepDTO pepDTO) {
        Pep pep = this.validateExistPep(id);
        this.validateForUpdatePep(pepDTO,pep);

        Pep pepUpdated = this.pepRepository.save(pep);
        this.createLog(pep.getId(),MESSAGEUPDATED);
        BeanUtils.copyProperties(pepUpdated,pepDTO);
        return new GetPepDTO(pepDTO);
    }

    @Override
    public List<GetPepDTO> getAll() {
        List<Pep> peps= this.pepRepository.findAll();
        if(peps.isEmpty()){
            String pepNotFoundMessage = messageSource.getMessage("noExist.pep.database",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(pepNotFoundMessage);
        }
        return this.list(peps);
    }

    @Override
    public void deleteById(UUID id) {
        Pep pep = this.validateExistPep(id);
        this.pepLogService.deleteAllByPepId(id);
        this.pepRepository.delete(pep);
    }

    @Override
    public void updateDatePep(LocalDateTime date) {

    }

    @Override
    public List<GetPepDTO> getAllByStatus(EStatePatient status) {
        List<Pep> peps= this.pepRepository.findAllByStatus(status);


        return this.list(peps);
    }

    @Override
    public long getNumInStatus(EStatePatient status){
        return this.pepRepository.countByStatus(status);
    }

    private Pep validateExistPep(UUID pepId){
        Optional<Pep> pepOptional =  pepRepository.findById(pepId);
        if (pepOptional.isEmpty()) {
            String pepNotFoundMessage = messageSource.getMessage("noexist.pepId.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(pepNotFoundMessage);
        }
        return pepOptional.get();
        }
    private void validateExistPepNumber(Integer pepNumber){
        boolean pep =  this.pepRepository.findByPepNumber(pepNumber).isPresent();
        if (pep){
            String pepNotFoundMessage = messageSource.getMessage("noexist.pepId.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(pepNotFoundMessage);
        }


    }
    private List<GetPepDTO> list(List<Pep> peps){
        List<GetPepDTO> pepDTOS = new ArrayList<>();
        peps.forEach(pep -> {
            PepDTO pepDTO = new PepDTO();
            BeanUtils.copyProperties(pep,pepDTO);
            pepDTOS.add(new GetPepDTO(pepDTO));
        });
        return pepDTOS;
    }
    private void createLog(UUID id, String message){
        PepLogDTO pepLogDTO = new PepLogDTO();
        Pep pep = new Pep();
        pep.setId(id);
        pepLogDTO.setPep(pep);
        pepLogDTO.setAction(message);
        pepLogDTO.setCreatedAt(LocalDateTime.now());
        this.pepLogService.create(pepLogDTO);

    }
    private void validateForUpdatePep(PutPepDTO pepDTO, Pep pep){


        if(pepDTO.getStatus() != null){
            pep.setStatus(pepDTO.getStatus());
        }

        if(pepDTO.getAllergies() != null){
            pep.setAllergies(pepDTO.getAllergies());
        }
        if(pepDTO.getBloodType() != null){
            pep.setBloodType(pepDTO.getBloodType());
        }

        if(pepDTO.getPrescription() != null){
            pep.setPrescription(pepDTO.getPrescription());
        }
        pep.setUpdateAt(LocalDateTime.now());
    }
}
