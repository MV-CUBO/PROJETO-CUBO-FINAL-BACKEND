package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.repository.DoctorRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import br.com.mv.APIHealth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public DoctorDTO create(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorDTO, doctor);
        Doctor newDoctor = doctorRepository.save(doctor);

        BeanUtils.copyProperties(newDoctor, doctorDTO);
        return doctorDTO;
    }
    @Override
    public DoctorDTO getDoctorById(UUID id) {
        Doctor doctor =  doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doctor not found.")
        );

        DoctorDTO dto = new DoctorDTO();
        BeanUtils.copyProperties(doctor, dto);
        return dto;
    }

    @Override
    public List<Doctor> getAll() {
        return doctorRepository.findAll() ;
    }

    @Override
    public DoctorDTO update(UUID id, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found.")
        );

        doctorDTO.setId(doctor.getId());
        BeanUtils.copyProperties(doctorDTO, doctor);
        Doctor updatedDoctor = doctorRepository.save(doctor);

        BeanUtils.copyProperties(updatedDoctor, doctorDTO);
        return doctorDTO;
    }

    @Override
    public void delete(UUID id) {
        doctorRepository.findById(id)
                .map(doctor ->
                {
                    doctorRepository.delete(doctor);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Doctor not found."));
    }
}
