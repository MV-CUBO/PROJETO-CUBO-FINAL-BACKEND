package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.service.NurseService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/nurse")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public NurseDTO getNurseById(@PathVariable(name = "id") UUID id) {
        return this.nurseService.getNurseById(id);
    }


    @GetMapping
    @ResponseStatus(OK)
    public List<NurseDTO> getAll(){
        return this.nurseService.getAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public NurseDTO saveNurse(@RequestBody @Valid NurseDTO nurseDTO) {
        return this.nurseService.create(nurseDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public NurseDTO updateNurse(@PathVariable(name = "id") UUID id, @RequestBody @Valid NurseDTO nurseDTO) {
          return this.nurseService.update(id, nurseDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteNurse(@PathVariable(name = "id") UUID id) {
        this.nurseService.delete(id);
    }
}
