package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.service.NurseService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Nurse")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public NurseDTO getNurseById(@PathVariable(name = "id") UUID id) {
        return nurseService.getNurseById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public NurseDTO saveNurse(@RequestBody NurseDTO nurseDTO) {
        return nurseService.create(nurseDTO);
    }

    @PutMapping
    @ResponseStatus(OK)
    public NurseDTO updateNurse(@PathVariable(name = "id") UUID id, @RequestBody NurseDTO nurseDTO) {
        return nurseService.update(id, nurseDTO);
    }

    @DeleteMapping
    @ResponseStatus(OK)
    public void deleteNurse(@PathVariable(name = "id") UUID id) {
        nurseService.delete(id);
    }
}
