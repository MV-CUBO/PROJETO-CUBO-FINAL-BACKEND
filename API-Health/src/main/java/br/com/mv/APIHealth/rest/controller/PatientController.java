package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.rest.dto.PatientDTO;
import br.com.mv.APIHealth.service.PatientService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public PatientDTO create(@RequestBody @Valid PatientDTO patientDTO) {
        return this.patientService.create(patientDTO);
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public PatientDTO getById(@PathVariable(name = "id") UUID id) {
        return this.patientService.getPatientById(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<PatientDTO> getAll(){
        return this.patientService.getAll();
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public PatientDTO updateById(@PathVariable(name = "id") UUID id, @RequestBody @Valid PatientDTO patientDTO) {
        return this.patientService.updateById(id, patientDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") UUID id) {
        this.patientService.deleteById(id);
    }
}
