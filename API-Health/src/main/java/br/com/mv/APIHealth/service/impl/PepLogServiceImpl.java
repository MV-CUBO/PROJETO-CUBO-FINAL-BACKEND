package br.com.mv.APIHealth.service.impl;


import br.com.mv.APIHealth.domain.entity.PepLog;
import br.com.mv.APIHealth.domain.repository.PepLogRepository;


import br.com.mv.APIHealth.rest.dto.PepLogDTO;
import br.com.mv.APIHealth.service.PepLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PepLogServiceImpl implements PepLogService{

    private final PepLogRepository pepLogRepository;
    @Override
    public PepLogDTO create(PepLogDTO pepLogDTO) {
        PepLog pepLog = new PepLog();
        BeanUtils.copyProperties(pepLogDTO,pepLog);
        PepLog newPepLog = this.pepLogRepository.save(pepLog);
        BeanUtils.copyProperties(newPepLog,pepLogDTO);
        return pepLogDTO;
    }

    @Override
    public PepLogDTO getPepById(UUID id) {
        return null;
    }

    @Override
    public PepLogDTO updateById(UUID id, PepLogDTO pepLogDTO) {
        return null;
    }

    @Override
    public List<PepLogDTO> getAll() {
        List<PepLog> pepLogs= this.pepLogRepository.findAll();
        List<PepLogDTO> pepLogDTOS = new ArrayList();
        pepLogs.forEach(pepLog -> {
            PepLogDTO pepLogDTO = new PepLogDTO();
            BeanUtils.copyProperties(pepLog,pepLogDTO);
            pepLogDTOS.add(pepLogDTO);
        });

        return pepLogDTOS;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public List<PepLogDTO> getAllByPepId(UUID pepId) {
        List<PepLog> pepLogs= this.pepLogRepository.findAllByPepId(pepId);
        List<PepLogDTO> pepLogDTOS = new ArrayList();
        pepLogs.forEach(pepLog -> {
            PepLogDTO pepLogDTO = new PepLogDTO();
            BeanUtils.copyProperties(pepLog,pepLogDTO);
            pepLogDTOS.add(pepLogDTO);
        });
        return pepLogDTOS;
    }
}
