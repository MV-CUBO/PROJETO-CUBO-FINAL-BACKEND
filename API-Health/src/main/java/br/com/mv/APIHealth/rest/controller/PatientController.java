package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

}
