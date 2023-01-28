package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.domain.enums.EStatePatient;
import br.com.mv.APIHealth.rest.dto.*;
import br.com.mv.APIHealth.service.PepLogService;
import br.com.mv.APIHealth.service.PepService;
import br.com.mv.APIHealth.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/pep")

public class PepController {

    @Autowired
    private  PepService pepService;
    @Autowired
    private  PepLogService pepLogService;

    public PepController(PepService pepService) {
        this.pepService = pepService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<GetPepDTO> getAll(){
        return this.pepService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public GetPepDTO getPepById(@PathVariable(name = "id") UUID id){
        return this.pepService.getPepById(id);
    }
    @GetMapping("/{id}/logs")
    @ResponseStatus(OK)
    public List<GetPepLogDTO> getAllByPepId(@PathVariable(name = "id") UUID id){
        return this.pepLogService.getAllByPepId(id);
    }
    @GetMapping("/logs")
    @ResponseStatus(OK)
    public List<GetPepLogDTO> getAllLog(){
        return this.pepLogService.getAll();
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(OK)
    public List<GetPepDTO> getAllByStatus(@PathVariable(name = "status") EStatePatient string){
        return this.pepService.getAllByStatus(string);
    }
    @GetMapping("/status/{status}/count")
    @ResponseStatus(OK)
    public long getAllByStatusNum(@PathVariable(name = "status") EStatePatient string){
        return this.pepService.getNumInStatus(string);
    }

    @GetMapping("/{id}/patient")
    @ResponseStatus(OK)
    public GetPepDTO getPepByPatientId(@PathVariable(name = "id") UUID patientId){
        return this.pepService.findPepByPatientId(patientId);
    }

    @PostMapping
    public ResponseEntity<Response<GetPepDTO>> savePep(@RequestBody @Valid PepDTO pepDTO, BindingResult result) {
        Response<GetPepDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        GetPepDTO newPepDto = this.pepService.create(pepDTO);
        response.setData(newPepDto);
        response.getErrors().add("No content.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<GetPepDTO>> updatePep(@PathVariable(name = "id") UUID id, @RequestBody PutPepDTO pepDTO, BindingResult result) {
        Response<GetPepDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        GetPepDTO updatedDto = pepService.updateById(id, pepDTO);
        response.setData(updatedDto);
        response.getErrors().add("No content.");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePep(@PathVariable(name = "id") UUID id) {
        this.pepService.deleteById(id);
    }
}
