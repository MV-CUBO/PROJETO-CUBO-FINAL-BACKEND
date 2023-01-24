package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.service.NurseService;
import br.com.mv.APIHealth.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

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
    public List<NurseDTO> getAll() {
        return this.nurseService.getAll();
    }

    @PostMapping
    public ResponseEntity<Response<NurseDTO>> saveNurse(@RequestBody @Valid NurseDTO nurseDTO,
                                                        BindingResult result) {
        Response<NurseDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        NurseDTO newNurseDto = nurseService.create(nurseDTO);
        response.setData(newNurseDto);
        response.getErrors().add("No content.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<NurseDTO>> updateUser(@PathVariable(name = "id") UUID id,
                                                         @RequestBody @Valid NurseDTO nurseDTO,
                                                         BindingResult result) {
        Response<NurseDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        NurseDTO updatedDto = nurseService.update(id, nurseDTO);
        response.setData(updatedDto);
        response.getErrors().add("No content.");

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteNurse(@PathVariable(name = "id") UUID id) {
        this.nurseService.delete(id);
    }
}
