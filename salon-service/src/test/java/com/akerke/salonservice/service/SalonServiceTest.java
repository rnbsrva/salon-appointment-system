package com.akerke.salonservice.service;

import com.akerke.salonservice.common.constants.AppConstants;
import com.akerke.salonservice.domain.dto.AddressDTO;
import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.entity.Address;
import com.akerke.salonservice.domain.entity.Salon;
import com.akerke.salonservice.domain.entity.User;
import com.akerke.salonservice.domain.service.AddressService;
import com.akerke.salonservice.domain.service.UserService;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.infrastructure.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.infrastructure.kafka.KafkaProducer;
import com.akerke.salonservice.domain.mapper.SalonMapper;
import com.akerke.salonservice.domain.repository.SalonRepository;
import com.akerke.salonservice.domain.service.impl.SalonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalonServiceTest {

    @Mock
    private SalonRepository salonRepository;
    @Mock
    private SalonMapper salonMapper;
    @Mock
    private AddressService addressService;
    @Mock
    private UserService userService;
    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private SalonServiceImpl salonService;

    private Salon salon;
    private SalonDTO salonDTO;

    @BeforeEach
    void setUp() {
        salonDTO = new SalonDTO(
                1L, "Akerke salon", "8 777 888 99 99", "email@gmail.com",
                new AddressDTO(
                        1L, "Street", "Street", "State"
                ),
                "desc"
        );

        var user = new User();
        user.setEmail("email@gmail.com");

        salon = new Salon();
        salon.setId(1L);
        salon.setOwner(user);
    }

    @Test
    void getById_whenValidSalonId_thenReturnSalon() {
        when(salonRepository.findById(salon.getId())).thenReturn(Optional.of(salon));

        var returnedSalon = salonService.getById(salon.getId());

        assertEquals(returnedSalon, salon);
    }

    @Test
    void getById_whenInvalidSalonId_thenThrowException() {
        when(salonRepository.findById(salon.getId())).thenReturn(Optional.empty());

        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> salonService.getById(salon.getId()));

        assertEquals("Salon with id: %d not found".formatted(salon.getId()), entityNotFoundException.getMessage());
    }

    @Test
    void getAll_whenRepositoryReturnEmptyList_thenReturnEmptyList() {
        when(salonRepository.findAll()).thenReturn(List.of());

        var salons = salonService.getAll();

        assertThat(salons).hasSize(0);
    }

    @Test
    void getAll_whenRepositoryReturnList_thenReturnSameList() {
        var salons = List.of(new Salon(), new Salon(), new Salon(), new Salon());

        when(salonRepository.findAll()).thenReturn(salons);

        var returnedSalons = salonService.getAll();

        assertEquals(salons, returnedSalons);
    }


    @Test
    void update_whenSalonExists_thenUpdateSalon() {
        Long id = 1L;
        when(salonRepository.findById(id)).thenReturn(Optional.of(salon));

        salonService.update(salonDTO, id);

        verify(salonMapper, times(1)).update(salonDTO, salon);
        verify(salonRepository, times(1)).save(salon);
    }

    @Test
    void update_whenSalonDoesNotExist_thenThrowException() {
        Long id = 1L;
        when(salonRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> salonService.update(salonDTO, id));
        verify(salonRepository, never()).save(any(Salon.class));
    }

    @Test
    void delete_whenValidSalonId_thenDeleteMaster() {
        when(salonRepository.findById(salon.getId())).thenReturn(Optional.of(salon));

        salonService.delete(salon.getId());

        verify(kafkaProducer).produce(
                AppConstants.ADMIN_TOPIC_NAME,
                new KafkaManageRoleRequest(salon.getId(), salon.getOwner().getEmail(), false)
        );

        verify(salonRepository).delete(salon);
    }

    @Test
    void delete_whenInvalidSalonId_thenThrowException() {
        when(salonRepository.findById(salon.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> salonService.delete(salon.getId()));

        verify(kafkaProducer, times(0)).produce(any(), any());
    }

    @Test
    void save() {
        when(salonMapper.toModel(salonDTO)).thenReturn(salon);
        when(addressService.save(salonDTO.addressDTO())).thenReturn(new Address());
        when(userService.getById(salonDTO.ownerId())).thenReturn(salon.getOwner());
        when(salonRepository.save(salon)).thenReturn(salon);

        var savedSalon = salonService.save(salonDTO);

        assertThat(savedSalon).isEqualTo(salon);

        verify(salonMapper).toModel(salonDTO);
        verify(addressService).save(salonDTO.addressDTO());
        verify(userService).getById(salonDTO.ownerId());
        verify(salonRepository).save(salon);
        verify(kafkaProducer).produce(AppConstants.ADMIN_TOPIC_NAME,
                new KafkaManageRoleRequest(savedSalon.getId(), savedSalon.getOwner().getEmail(), true)
        );
    }
}