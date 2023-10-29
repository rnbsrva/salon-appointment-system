package com.akerke.salonservice.service.impl;

import com.akerke.salonservice.constants.AppConstants;
import com.akerke.salonservice.dto.SalonDTO;
import com.akerke.salonservice.entity.Address;
import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.entity.User;
import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.kafka.KafkaProducer;
import com.akerke.salonservice.mapper.SalonMapper;
import com.akerke.salonservice.repository.SalonRepository;
import com.akerke.salonservice.service.*;
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

    @Override
    public Salon getById(Long id) {
        return salonRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Salon.class, id));
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

        return savedSalon;
    }

    @Override
    public void update(SalonDTO salonDTO, Long id) {
        var salon = this.getById(id);
        salonMapper.update(salonDTO, salon);
        salonRepository.save(salon);
    }

    @Override
    public void delete(Long id) {
        var salon = this.getById(id);

        kafkaProducer.produce(
                AppConstants.ADMIN_TOPIC_NAME,
                new KafkaManageRoleRequest(salon.getId(), salon.getOwner().getEmail(), false)
        );

        salonRepository.delete(salon);
    }
}
