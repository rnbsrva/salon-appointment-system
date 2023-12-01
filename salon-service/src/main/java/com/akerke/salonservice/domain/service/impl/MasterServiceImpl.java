package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.common.constants.AppConstants;
import com.akerke.salonservice.domain.dto.MasterDTO;
import com.akerke.salonservice.domain.entity.ImageMetadata;
import com.akerke.salonservice.domain.entity.Master;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.repository.MasterRepository;
import com.akerke.salonservice.domain.service.MasterService;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.domain.service.TreatmentService;
import com.akerke.salonservice.domain.service.UserService;
import com.akerke.salonservice.infrastructure.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.infrastructure.kafka.KafkaProducer;
import com.akerke.salonservice.domain.mapper.MasterMapper;
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
        return masterRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Master.class, id));
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

    @Override
    public void addImage(Long id, String imageId, Boolean isMainImage) {
        var master = this.getById(id);
        master.getImageMetadata().add(new ImageMetadata(imageId, isMainImage));
        masterRepository.save(master);
    }


}
