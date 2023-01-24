package br.com.mv.APIHealth.service.impl;


import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.entity.Pep;
import br.com.mv.APIHealth.domain.enums.EStatePatient;
import br.com.mv.APIHealth.domain.repository.DoctorRepository;
import br.com.mv.APIHealth.domain.repository.PatientRepository;
import br.com.mv.APIHealth.domain.repository.PepRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.PepDoctorDTO;
import br.com.mv.APIHealth.rest.dto.PepLogDTO;
import br.com.mv.APIHealth.rest.dto.PepPatientDTO;
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
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MessageSource messageSource;

    private final String MESSAGECREATED = "pep was created";
    private final String MESSAGEUPDATED = "pep was updated";

    @Override
    public PepDTO create(PepDTO pepDTO) {
        this.validateExistPepNumber( pepDTO.getPepNumber());
        Pep pep = new Pep();

        BeanUtils.copyProperties(pepDTO,pep);
        Optional<Doctor>doctor  = this.doctorRepository.findById(pepDTO.getDoctor().getId());
        Optional<Patient> patient = this.patientRepository.findById(pepDTO.getPatient().getId());
        if(!doctor.isPresent()) throw new ResourceNotFoundException("{noexist.doctor.field}");
        if(!patient.isPresent()) throw new ResourceNotFoundException("{noexist.patient.field}");
        PepDoctorDTO doctorDTO = new PepDoctorDTO();
        BeanUtils.copyProperties(doctor.get(),doctorDTO);
        Doctor newDoctor = new Doctor();
        BeanUtils.copyProperties(doctorDTO,newDoctor);
        pep.setDoctor(newDoctor);
        PepPatientDTO patientDTO = new PepPatientDTO();
        BeanUtils.copyProperties(patient.get(),patientDTO);
        Patient newPatient = new Patient();
        BeanUtils.copyProperties(patientDTO,newPatient);
        pep.setPatient(newPatient);
        pep.setCreatedAt(LocalDateTime.now());
        pep.setUpdateAt(LocalDateTime.now());
        pep.setStatus(EStatePatient.CONSULTATION);
        Pep newPep = this.pepRepository.save(pep);
        PepLogDTO pepLogDTO = new PepLogDTO();
        pepLogDTO.setPepId(pep.getId());
        pepLogDTO.setAction(MESSAGECREATED);
        pepLogDTO.setCreatedAt(LocalDateTime.now());
        this.pepLogService.create(pepLogDTO);
        BeanUtils.copyProperties(newPep,pepDTO);
        return pepDTO;
    }

    @Override
    public PepDTO getPepById(UUID id) {

        Optional<Pep> pepOptional =  pepRepository.findById(id);
        if (pepOptional.isEmpty()) {
            String pepNotFoundMessage = messageSource.getMessage("noexist.pepId.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(pepNotFoundMessage);
        }

        PepDTO pepDTO = new PepDTO();
        BeanUtils.copyProperties(pepOptional,pepDTO);
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
        PepLogDTO pepLogDTO = new PepLogDTO();
        pepLogDTO.setPepId(pep.getId());
        pepLogDTO.setAction(MESSAGEUPDATED);
        pepLogDTO.setCreatedAt(LocalDateTime.now());
        this.pepLogService.create(pepLogDTO);
        BeanUtils.copyProperties(pepUpdated,pepDTO);
        return pepDTO;
    }

    @Override
    public List<PepDTO> getAll() {
        List<Pep> peps= this.pepRepository.findAll();

        if(peps.isEmpty()
        ){
            String pepNotFoundMessage = messageSource.getMessage("noExist.pep.database",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(pepNotFoundMessage);
        }


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
        return this.pepRepository.findById(pepId).orElseThrow(() -> new ResourceNotFoundException("{noexist.pepId.field}"));
    }
    private void validateExistPepNumber(String pepNumber){
        boolean pep =  this.pepRepository.findByPepNumber(pepNumber).isPresent();
        if (pep){
            throw new BadRequestException("{exist.pepNumber.field}");
        }


    }
    private void validateForUpdatePep(PepDTO pepDTO, Pep pep){

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
        if(pepDTO.getBloodType() != null){
            pep.setBloodType(pepDTO.getBloodType());
        }

        if(pepDTO.getPrescription() != null){
            pep.setPrescription(pepDTO.getPrescription());
        }
        if(pepDTO.getUpdateAt() != null){
            pep.setUpdateAt(pepDTO.getUpdateAt());
        }
    }
}
