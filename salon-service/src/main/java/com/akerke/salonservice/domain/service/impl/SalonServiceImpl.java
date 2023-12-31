package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.common.constants.AppConstants;
import com.akerke.salonservice.common.payload.SalonSearch;
import com.akerke.salonservice.common.specs.SalonSpecifications;
import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.entity.ImageMetadata;
import com.akerke.salonservice.domain.entity.Salon;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.repository.ImageMetadataRepository;
import com.akerke.salonservice.domain.repository.SalonRepository;
import com.akerke.salonservice.domain.service.AddressService;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.domain.service.UserService;
import com.akerke.salonservice.infrastructure.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.infrastructure.kafka.KafkaProducer;
import com.akerke.salonservice.domain.mapper.SalonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final ImageMetadataRepository imageMetadataRepository;
    private final SalonRepository salonRepository;
    private final SalonMapper salonMapper;
    private final AddressService addressService;
    private final UserService userService;
    private final KafkaProducer kafkaProducer;
    private final JdbcTemplate jdbcTemplate;

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

            salonRepository.deleteById(id);
    }

    @Override
    public Page<Salon> find(SalonSearch salonSearch, int page, int size) {
        var spec = SalonSpecifications.buildSpecification(salonSearch);
        return salonRepository.findAll(spec, PageRequest.of(page,size));
    }

    @Override
    public void addImage(Long id, String imageId, Boolean isMainImage) {
        var salon = this.getById(id);
        if(isMainImage){
            salon.getImageMetadata().forEach(imageMetadata -> imageMetadata.setIsMainImage(false));
        }
        salon.getImageMetadata().add(new ImageMetadata(imageId, isMainImage));
        salonRepository.save(salon);
    }

    @Override
    public void deleteImage(String imageId) {
        var imageMetadataOptional = imageMetadataRepository.findImageMetadataByImageId(imageId);

        if(imageMetadataOptional.isEmpty()){
            throw new EntityNotFoundException(ImageMetadata.class, imageId);
        }
        var image = imageMetadataOptional.get();

        jdbcTemplate.update("DELETE FROM salon_image_metadata WHERE image_metadata_id = ?", image.getId());
        imageMetadataRepository.delete(image);
    }
}
