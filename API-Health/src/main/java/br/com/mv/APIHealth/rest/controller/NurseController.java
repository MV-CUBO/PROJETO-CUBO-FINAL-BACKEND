package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.domain.entity.Nurse;
import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.service.NurseService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
        return nurseService.getNurseById(id);
    }

    //modificar para DTO de informações e tratar os dados por ele
    @GetMapping
    @ResponseStatus(OK)
    public List<Nurse> getAll(){
        return nurseService.getAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public NurseDTO saveNurse(@RequestBody @Valid NurseDTO nurseDTO) {
        return nurseService.create(nurseDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public NurseDTO updateNurse(@PathVariable(name = "id") UUID id, @RequestBody @Valid NurseDTO nurseDTO) {
          return nurseService.update(id, nurseDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteNurse(@PathVariable(name = "id") UUID id) {
        nurseService.delete(id);
    }
}
