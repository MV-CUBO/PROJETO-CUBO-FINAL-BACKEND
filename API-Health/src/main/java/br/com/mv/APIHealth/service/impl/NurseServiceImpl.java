package br.com.mv.APIHealth.service.impl;


import br.com.mv.APIHealth.domain.entity.Nurse;
import br.com.mv.APIHealth.domain.repository.NurseRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.service.NurseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
@RequiredArgsConstructor
public class NurseServiceImpl implements NurseService {

    private final NurseRepository nurseRepository;

    @Override
    public NurseDTO create(NurseDTO nurseDTO) {
        Nurse nurse = new Nurse();
        BeanUtils.copyProperties(nurseDTO, nurse);
        Nurse newNurse = nurseRepository.save(nurse);

        BeanUtils.copyProperties(newNurse, nurseDTO);
        return nurseDTO;
    }
    @Override
    public NurseDTO getPatientById(UUID id) {
            Nurse nurse =  nurseRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Nurse not found.")
            );

            NurseDTO dto = new NurseDTO();
            BeanUtils.copyProperties(nurse, dto);
            return dto;
    }

    @Override
    public List<Nurse> getAll() {
        return nurseRepository.findAll() ;
    }

    @Override
    public NurseDTO update(UUID id, NurseDTO nurseDTO) {
        Nurse nurse = nurseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found.")
        );

        nurseDTO.setId(nurse.getId());
        BeanUtils.copyProperties(nurseDTO, nurse);
        Nurse updatedNurse = nurseRepository.save(nurse);

        BeanUtils.copyProperties(updatedNurse, nurseDTO);
        return nurseDTO;
    }

    @Override
    public void deleteById(UUID id) {
        nurseRepository.findById(id)
                .map(nurse ->
                {
                    nurseRepository.delete(nurse);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Nurse not found."));
    }
}
