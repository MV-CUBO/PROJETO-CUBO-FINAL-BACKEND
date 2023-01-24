package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.Nurse;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import br.com.mv.APIHealth.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/doctor")
public class DoctorController {


    private final DoctorService doctorService;


    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public DoctorDTO getDoctorById(@PathVariable(name = "id") UUID id) {
        return this.doctorService.getDoctorById(id);
    }

    //modificar para DTO de informações e tratar os dados por ele
    @GetMapping
    @ResponseStatus(OK)
    public List<DoctorDTO> getAll(){
        return this.doctorService.getAll();
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public DoctorDTO saveDoctor(@RequestBody @Valid DoctorDTO doctorDTO) {
        return this.doctorService.create(doctorDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public DoctorDTO updateDoctor(@PathVariable(name = "id") UUID id, @RequestBody @Valid DoctorDTO doctorDTO) {
        return this.doctorService.update(id, doctorDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable(name = "id") UUID id) {
        this.doctorService.delete(id);
    }
}
