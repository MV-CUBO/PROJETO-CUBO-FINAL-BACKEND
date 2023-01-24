package br.com.mv.APIHealth.rest.controller;


import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.PepLogDTO;
import br.com.mv.APIHealth.service.PepLogService;
import br.com.mv.APIHealth.service.PepService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ResponseStatus(CREATED)
    public PepDTO savePep(@RequestBody @Valid PepDTO pepDTO) {
        return this.pepService.create(pepDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public PepDTO updatePep(@PathVariable(name = "id") UUID id, @RequestBody @Valid PepDTO pepDTO) {
        return this.pepService.updateById(id, pepDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePep(@PathVariable(name = "id") UUID id) {
        this.pepService.deleteById(id);
    }
}
