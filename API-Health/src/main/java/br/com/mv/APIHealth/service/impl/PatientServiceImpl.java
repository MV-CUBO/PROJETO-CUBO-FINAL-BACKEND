package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.entity.Pep;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.enums.Gender;
import br.com.mv.APIHealth.domain.repository.PatientRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.PatientDTO;
import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.UpdatePatientDTO;
import br.com.mv.APIHealth.service.AddressService;
import br.com.mv.APIHealth.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {

    private final  PatientRepository patientRepository;


    private final MessageSource messageSource;

    private final AddressService addressService;

    private final String MESSAGE = "Provide the patient's address.";

    public PatientServiceImpl(PatientRepository patientRepository, MessageSource messageSource, AddressService addressService) {
        this.patientRepository = patientRepository;
        this.messageSource = messageSource;
        this.addressService = addressService;
    }

    @Override
    public PatientDTO create(PatientDTO patientDTO) {
        this.validatePatientExistByCpf(patientDTO.getCpf());

        this.stepsForCreationPatient(patientDTO);

        Patient patient = new Patient();

        BeanUtils.copyProperties(patientDTO, patient);

        Patient newPatient = this.patientRepository.save(patient);

        BeanUtils.copyProperties(newPatient, patientDTO);

        return patientDTO;
    }

    @Override
    public PatientDTO getPatientById(UUID id) {

        Patient patientOptional =  this.validatePatientExists(id);

        PatientDTO patientDTO = new PatientDTO();

        BeanUtils.copyProperties(patientOptional, patientDTO);

        return patientDTO;
    }

    @Override
    public PepDTO findPepByPatientId(UUID id) {

        this.validatePatientExists(id);

        Pep patientPep = this.patientRepository.findPepById(id).get();

        PepDTO patientPepDTO = new PepDTO();

        BeanUtils.copyProperties(patientPep, patientPepDTO);

        return patientPepDTO;
    }

    @Override
    public UpdatePatientDTO updateById(UUID id, UpdatePatientDTO patientDTO) {
        Patient patient = this.validatePatientExists(id);

        this.validateForUpdatePatient(patientDTO, patient);

        patientDTO.setId(patientDTO.getId());

        BeanUtils.copyProperties(patient, patientDTO);

        Patient patientUpdated = this.patientRepository.save(patient);

        BeanUtils.copyProperties(patientUpdated, patientDTO);
        return patientDTO;
    }

    @Override
    public List<PatientDTO> getAll() {
        List<Patient> patients= this.patientRepository.findAll();

        if(patients.isEmpty()){
            String patientNotFoundMessage = messageSource.getMessage("noExist.patient.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(patientNotFoundMessage);
        }

        List<PatientDTO> patientsDTO = new ArrayList<>();

        patients.forEach(patient -> {
            PatientDTO patientDTO = new PatientDTO();

            BeanUtils.copyProperties(patient, patientDTO);

            patientsDTO.add(patientDTO);
        });

        return patientsDTO;
    }

    @Override
    public Long countPatientByStatus(EStatus value) {
        return patientRepository.countPatientByStatus(value);
    }

    @Override
    public Long countPatientByGender(Gender value) {
        return patientRepository.countPatientByGender(value);
    }

    @Override
    public void deleteById(UUID id) {
        Patient patient = this.validatePatientExists(id);

        this.addressService.deleteById(patient.getAddress().getId());
        this.patientRepository.deleteById(id);
    }

    private PatientDTO stepsForCreationPatient(PatientDTO patientDTO) {
        Address newAddress = this.createAddressForPatient(patientDTO.getAddress());

        patientDTO.setAddress(newAddress);

        patientDTO.setCreatedAt(LocalDateTime.now());
        patientDTO.setUpdateAT(LocalDateTime.now());

        patientDTO.setStatus(EStatus.ACTIVATE);

        return patientDTO;
    }

    private Patient validatePatientExists (UUID id) {
        Optional<Patient> patientOptional =  patientRepository.findById(id);

        if (patientOptional.isEmpty()) {
            String patientNotFoundMessage = messageSource.getMessage("noExist.idPatient.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(patientNotFoundMessage);
        }

        return patientOptional.get();
    }

    private void validateForUpdatePatient(UpdatePatientDTO patientDTO, Patient patient) {
        patient.setName(patientDTO.getName() != null ? patientDTO.getName() : patient.getName());

        patient.setPhone(patientDTO.getPhone() != null ? patientDTO.getPhone() : patient.getPhone());

        patient.setStatus(patientDTO.getStatus() != null ? patientDTO.getStatus() : patient.getStatus());

        patient.setObservation(patientDTO.getObservation() != null ? patientDTO.getObservation() : patient.getObservation());

        patient.setMaritalStatus(patientDTO.getMaritalStatus() != null ? patientDTO.getMaritalStatus() : patient.getMaritalStatus());

        patient.setHealthInsurenceCard(patientDTO.getHealthInsurenceCard() != null ? patientDTO.getHealthInsurenceCard() : patient.getHealthInsurenceCard());

        patient.setGender(patientDTO.getGender() != null ? patientDTO.getGender() : patient.getGender());

        patient.setEmail(patientDTO.getEmail() != null ? patientDTO.getEmail() : patient.getEmail());

        patient.setInsuranceCompany(patientDTO.getInsuranceCompany() != null ? patientDTO.getInsuranceCompany() : patient.getInsuranceCompany());

        patient.setDateOfBirth(patientDTO.getDateOfBirth() != null ? patientDTO.getDateOfBirth() : patient.getDateOfBirth());

        if (patientDTO.getAddress() != null && patient.getAddress() != null) {
            this.forUpdateAddressPatient(patientDTO.getAddress(), patient.getAddress());
        }

        patient.setUpdateAT(LocalDateTime.now());
    }

    private Address createAddressForPatient (Address addressDto) {
        if(addressDto != null) {
            if (
                    addressDto.getZipCode() == null ||
                    addressDto.getStreet() == null ||
                    addressDto.getNumber() == null ||
                    addressDto.getDistrict() == null ||
                    addressDto.getCity() == null ||
                    addressDto.getState() == null
            ) {
                String addressValidationFields = messageSource.getMessage("required.address.field",
                        null, Locale.getDefault());
                throw new BadRequestException(addressValidationFields);
            } else {
                addressDto =  new Address(
                        null,
                        addressDto.getZipCode(),
                        addressDto.getStreet(),
                        addressDto.getNumber(),
                        addressDto.getDistrict(),
                        addressDto.getCity(),
                        addressDto.getState(),
                        addressDto.getComplements()
                );
            }
        }

        return this.addressService.create(addressDto, MESSAGE);
    }

    private void validatePatientExistByCpf(String cpf) {
        boolean patientIsPresent = this.patientRepository.findByCpf(cpf).isPresent();

        if (patientIsPresent) {
            String patientNotFoundMessage = messageSource.getMessage("Exist.cpf.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(patientNotFoundMessage);
        }
    }

    private void forUpdateAddressPatient(Address addressDto, Address addressEntity) {

        addressEntity.setZipCode(addressDto.getZipCode() != null ? addressDto.getZipCode() : addressEntity.getZipCode());

        addressEntity.setCity(addressDto.getCity() != null ? addressDto.getCity() : addressEntity.getCity());

        addressEntity.setNumber(addressDto.getNumber() != null ? addressDto.getNumber() : addressEntity.getNumber());

        addressEntity.setComplements(addressDto.getComplements()!= null ? addressDto.getComplements() : addressEntity.getComplements());

        addressEntity.setStreet(addressDto.getStreet() != null ? addressDto.getStreet() : addressEntity.getStreet());

        addressEntity.setState(addressDto.getState() != null ? addressDto.getState() : addressEntity.getState());

        addressEntity.setDistrict(addressDto.getDistrict() != null ? addressDto.getDistrict() : addressEntity.getDistrict());

        this.addressService.updateAddressById(addressEntity);
    }
}
