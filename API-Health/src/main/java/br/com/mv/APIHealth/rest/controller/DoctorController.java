package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.Nurse;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import br.com.mv.APIHealth.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Doctor")
public class DoctorController {


    private final DoctorService doctorService;


    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public DoctorDTO getDoctorById(@PathVariable(name = "id") UUID id) {
        return doctorService.getDoctorById(id);
    }

    //modificar para DTO de informações e tratar os dados por ele
    @GetMapping
    @ResponseStatus(OK)
    public List<Doctor> getAll(){
        return this.doctorService.getAll();
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public DoctorDTO saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.create(doctorDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public DoctorDTO updateDoctor(@PathVariable(name = "id") UUID id, @RequestBody DoctorDTO doctorDTO) {
        return doctorService.update(id, doctorDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(OK)
    public void deleteDoctor(@PathVariable(name = "id") UUID id) {
        doctorService.delete(id);
    }
}
