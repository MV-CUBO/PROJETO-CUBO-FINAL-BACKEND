package br.com.mv.APIHealth.service;


import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import br.com.mv.APIHealth.rest.dto.UpdateDoctorDTO;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    public DoctorDTO create (DoctorDTO doctorDTO);

    public DoctorDTO getDoctorById (UUID id);

    public Long countDoctorByStatus(EStatus value);
    public List<DoctorDTO> getAll ();

    public UpdateDoctorDTO update (UUID id, UpdateDoctorDTO doctorDTO );

    public void delete (UUID id);




}
