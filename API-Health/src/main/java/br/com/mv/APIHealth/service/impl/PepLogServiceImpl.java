package br.com.mv.APIHealth.service.impl;


import br.com.mv.APIHealth.domain.entity.PepLog;
import br.com.mv.APIHealth.domain.repository.PepLogRepository;
import br.com.mv.APIHealth.domain.repository.PepRepository;
import br.com.mv.APIHealth.rest.dto.GetPepLogDTO;
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

    private final PepRepository pepRepository;
    @Override
    public GetPepLogDTO create(PepLogDTO pepLogDTO) {
        PepLog pepLog = new PepLog();
        BeanUtils.copyProperties(pepLogDTO,pepLog);
        PepLog newPepLog = this.pepLogRepository.save(pepLog);
        BeanUtils.copyProperties(newPepLog,pepLogDTO);
        return new GetPepLogDTO(pepLogDTO);
    }

    @Override
    public GetPepLogDTO getPepById(UUID id) {
        return null;
    }

    @Override
    public GetPepLogDTO updateById(UUID id, PepLogDTO pepLogDTO) {
        return null;
    }


    @Override
    public List<GetPepLogDTO> getAll() {
        List<PepLog> pepLogs= this.pepLogRepository.findAll();
       return this.list(pepLogs);
    }

    @Override
    public void deleteById(UUID id) {

    }
    @Override
    public void deleteAllByPepId(UUID pepId){
        List<PepLog> pepLogs= this.pepLogRepository.findAllByPepId(pepId);
        pepLogs.forEach(pepLog -> {
            this.pepLogRepository.delete(pepLog);
        });
    }

    @Override
    public List<GetPepLogDTO> getAllByPepId(UUID pepId) {
        List<PepLog> pepLogs= this.pepLogRepository.findAllByPepId(pepId);

        return this.list(pepLogs);
    }
    private List<GetPepLogDTO> list(List<PepLog> pepLogs){
        List<GetPepLogDTO> pepLogDTOS = new ArrayList<>();
        pepLogs.forEach(pepLog -> {
            PepLogDTO pepLogDTO = new PepLogDTO();
            BeanUtils.copyProperties(pepLog,pepLogDTO);
            pepLogDTOS.add(new GetPepLogDTO(pepLogDTO));
        });

        return pepLogDTOS;
    }
}
