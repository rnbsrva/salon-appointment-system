package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.common.constants.AppConstants;
import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.entity.Salon;
import com.akerke.salonservice.domain.repository.SalonRepository;
import com.akerke.salonservice.domain.service.AddressService;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.domain.service.UserService;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.infrastructure.elastic.service.SalonElasticService;
import com.akerke.salonservice.infrastructure.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.infrastructure.kafka.KafkaProducer;
import com.akerke.salonservice.domain.mapper.SalonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;
    private final SalonMapper salonMapper;
    private final AddressService addressService;
    private final UserService userService;
    private final KafkaProducer kafkaProducer;
    private final SalonElasticService salonElasticService;

    @Override
    public Salon getById(Long id) {
        return salonRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Salon.class, id));
    }

    @Override
    public List<Salon> getAll() {
        return salonRepository.findAll();
    }

    @Override
    public Salon save(SalonDTO salonDTO) {
        var salon = salonMapper.toModel(salonDTO);
        salon.setAddress(addressService.save(salonDTO.addressDTO()));
        salon.setOwner(userService.getById(salonDTO.ownerId()));

        var savedSalon = salonRepository.save(salon);

        kafkaProducer.produce(
                AppConstants.ADMIN_TOPIC_NAME,
                new KafkaManageRoleRequest(savedSalon.getId(), savedSalon.getOwner().getEmail(), true)
        );

        salonElasticService.save(savedSalon);
        return savedSalon;
    }

    @Override
    public void update(SalonDTO salonDTO, Long id) {
        var salon = this.getById(id);
        salonMapper.update(salonDTO, salon);
        salonElasticService.save(
                salonRepository.save(salon)
        );
    }

    @Override
    public void delete(Long id) {
        var salon = this.getById(id);

        kafkaProducer.produce(
                AppConstants.ADMIN_TOPIC_NAME,
                new KafkaManageRoleRequest(salon.getId(), salon.getOwner().getEmail(), false)
        );

        salonRepository.delete(salon);
        salonElasticService.delete(salon);
    }
}
