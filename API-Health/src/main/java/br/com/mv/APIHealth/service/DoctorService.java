package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    public DoctorDTO create (DoctorDTO doctorDTO);

    public DoctorDTO getDoctorById (UUID id);

    public List<Doctor> getAll ();

    public DoctorDTO update (UUID id, DoctorDTO doctorDTO );

    public void delete (UUID id);




}
