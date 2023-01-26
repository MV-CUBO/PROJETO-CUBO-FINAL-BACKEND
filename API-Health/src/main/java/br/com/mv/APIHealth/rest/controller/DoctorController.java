package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import br.com.mv.APIHealth.service.DoctorService;
import br.com.mv.APIHealth.utils.Response;
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
@RequestMapping("/api/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public DoctorDTO getDoctorById(@PathVariable(name = "id") UUID id) {
        return this.doctorService.getDoctorById(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<DoctorDTO> getAll(){
        return this.doctorService.getAll();
    }

    @GetMapping("/count/status")
    public ResponseEntity<Long> countByStatus(@RequestParam("status") EStatus value) {
        Long count = this.doctorService.countDoctorByStatus(value);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<Response<DoctorDTO>> saveDoctor(@RequestBody @Valid DoctorDTO doctorDTO,
                                                          BindingResult result) {
        Response<DoctorDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        DoctorDTO newDoctorDto = doctorService.create(doctorDTO);
        response.setData(newDoctorDto);
        response.getErrors().add("No content.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<DoctorDTO>> updateUser(@PathVariable(name = "id") UUID id,
                                                         @RequestBody @Valid DoctorDTO doctorDTO,
                                                         BindingResult result) {
        Response<DoctorDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        DoctorDTO updatedDto = doctorService.update(id, doctorDTO);
        response.setData(updatedDto);
        response.getErrors().add("No content.");

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable(name = "id") UUID id) {
        this.doctorService.delete(id);
    }
}
