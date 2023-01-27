package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.enums.Gender;
import br.com.mv.APIHealth.rest.dto.PatientDTO;
import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.UpdatePatientDTO;
import br.com.mv.APIHealth.service.PatientService;
import br.com.mv.APIHealth.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Response<PatientDTO>> create(@RequestBody @Valid PatientDTO patientDTO, BindingResult result) {
        Response<PatientDTO> response = new Response<>();

        if(result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        PatientDTO newPatientDto = this.patientService.create(patientDTO);
        response.setData(newPatientDto);

        response.getErrors().add("No content");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public PatientDTO getById(@PathVariable(name = "id") UUID id) {
        return this.patientService.getPatientById(id);
    }

    @GetMapping("/pep/{id}")
    @ResponseStatus(OK)
    public PepDTO findPepByPatientId(@PathVariable(name = "id") UUID id) {
        return this.patientService.findPepByPatientId(id);
    }

    @GetMapping("/count/status")
    public ResponseEntity<Long> countByStatus(@RequestParam("status") EStatus value) {
        Long count = patientService.countPatientByStatus(value);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/count/gender")
    public ResponseEntity<Long> countByGender(@RequestParam("gender") Gender value) {
        Long count = patientService.countPatientByGender(value);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<PatientDTO> getAll(){
        return this.patientService.getAll();
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<UpdatePatientDTO>> updateById(
                                       @PathVariable(name = "id") UUID id,
                                       @RequestBody @Valid UpdatePatientDTO patientDTO,
                                       BindingResult result) {

        Response<UpdatePatientDTO> response = new Response<>();

        if(result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        UpdatePatientDTO updatePatientDTO = this.patientService.updateById(id, patientDTO);
        response.setData(updatePatientDTO);
        response.getErrors().add("No content.");

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") UUID id) {
        this.patientService.deleteById(id);
    }
}
