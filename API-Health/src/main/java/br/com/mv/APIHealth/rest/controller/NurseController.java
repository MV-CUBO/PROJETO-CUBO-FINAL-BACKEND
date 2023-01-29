package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.rest.dto.UpdateNurseDTO;
import br.com.mv.APIHealth.service.NurseService;
import br.com.mv.APIHealth.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(tags = "Nurse", summary = "Get a nurse by ID")
    public NurseDTO getNurseById(@PathVariable(name = "id") UUID id) {
        return this.nurseService.getNurseById(id);
    }


    @GetMapping
    @ResponseStatus(OK)
    @Operation(tags = "Nurse", summary = "Get all nurses")
    public List<NurseDTO> getAll() {
        return this.nurseService.getAll();
    }

    @PostMapping
    @Operation(tags = "Nurse", summary = "Create a new nurse")
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
    @Operation(tags = "Nurse", summary = "Update an existing nurse")
    public ResponseEntity<Response<UpdateNurseDTO>> updateUser(@PathVariable(name = "id") UUID id,
                                                               @RequestBody @Valid UpdateNurseDTO nurseDTO,
                                                               BindingResult result) {
        Response<UpdateNurseDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        UpdateNurseDTO updatedDto = nurseService.update(id, nurseDTO);
        response.setData(updatedDto);
        response.getErrors().add("No content.");

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(tags = "Nurse", summary = "Delete a nurse")
    public void deleteNurse(@PathVariable(name = "id") UUID id) {
        this.nurseService.delete(id);
    }
}
