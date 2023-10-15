package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.constants.AppConstants;
import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.entity.Master;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.kafka.KafkaProducer;
import com.akerke.salonservice.mapper.MasterMapper;
import com.akerke.salonservice.repository.MasterRepository;
import com.akerke.salonservice.service.MasterService;
import com.akerke.salonservice.service.SalonService;
import com.akerke.salonservice.service.TreatmentService;
import com.akerke.salonservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final MasterMapper masterMapper;
    private final UserService userService;
    private final TreatmentService treatmentService;
    private final SalonService salonService;
    private final KafkaProducer kafkaProducer;

    @Override
    public Master save(MasterDTO masterDTO) {
        var master = masterMapper.toModel(masterDTO);
        master.setUser(userService.getById(masterDTO.userId()));
        master.setSalon(salonService.getById(masterDTO.salonId()));

        var savedMaster = masterRepository.save(master);
        kafkaProducer.produce(
                AppConstants.MASTER_TOPIC_NAME,
                new KafkaManageRoleRequest(savedMaster.getSalonId(), savedMaster.getUser().getEmail(), true)
        );

        return savedMaster;
    }

    @Override
    public void delete(Long id) {
        var master = this.getById(id);

        kafkaProducer.produce(
                AppConstants.MASTER_TOPIC_NAME,
                new KafkaManageRoleRequest(master.getSalonId(), master.getUser().getEmail(), false)
        );

        masterRepository.delete(master);
    }

    @Override
    public void update(MasterDTO masterDTO, Long id) {
        var master = this.getById(id);
        masterMapper.update(masterDTO, master);
        masterRepository.save(master);
    }

    @Override
    public Master getById(Long id) {
        return masterRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Master.class, id));
    }

    @Override
    public void addTreatment(Long id, List<Long> treatmentIds) {
        var master = this.getById(id);
        var treatments = treatmentService.getAll(treatmentIds);
        master.getTreatments().addAll(treatments);
        masterRepository.save(master);
    }

    @Override
    public List<Master> getAll() {
        return masterRepository.findAll();
    }


}
