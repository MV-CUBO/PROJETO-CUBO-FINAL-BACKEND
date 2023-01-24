package br.com.mv.APIHealth.rest.controller;



import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.PepLogDTO;
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
    public List<PepDTO> getAll(){
        return this.pepService.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public PepDTO getPepById(@PathVariable(name = "id") UUID id){
        return this.pepService.getPepById(id);
    }
    @GetMapping("{id}/logs")
    @ResponseStatus(OK)
    public List<PepLogDTO> getAllByPepId(@PathVariable(name = "id") UUID id){
        return this.pepLogService.getAllByPepId(id);
    }
    @GetMapping("/logs")
    @ResponseStatus(OK)
    public List<PepLogDTO> getAllLog(){
        return this.pepLogService.getAll();
    }


    @PostMapping
    public ResponseEntity<Response<PepDTO>> savePep(@RequestBody @Valid PepDTO pepDTO, BindingResult result) {
        Response<PepDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        PepDTO newPepDto = this.pepService.create(pepDTO);
        response.setData(newPepDto);
        response.getErrors().add("No content.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<PepDTO>> updatePep(@PathVariable(name = "id") UUID id, @RequestBody @Valid PepDTO pepDTO, BindingResult result) {
        Response<PepDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        PepDTO updatedDto = pepService.updateById(id, pepDTO);
        response.setData(updatedDto);
        response.getErrors().add("No content.");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePep(@PathVariable(name = "id") UUID id) {
        this.pepService.deleteById(id);
    }
}
