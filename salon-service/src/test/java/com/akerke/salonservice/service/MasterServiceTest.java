package com.akerke.salonservice.service;

import com.akerke.salonservice.common.constants.AppConstants;
import com.akerke.salonservice.domain.dto.AddTreatmentDTO;
import com.akerke.salonservice.domain.dto.MasterDTO;
import com.akerke.salonservice.domain.entity.Master;
import com.akerke.salonservice.domain.entity.Salon;
import com.akerke.salonservice.domain.entity.Treatment;
import com.akerke.salonservice.domain.entity.User;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.domain.service.TreatmentService;
import com.akerke.salonservice.domain.service.UserService;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.infrastructure.kafka.KafkaManageRoleRequest;
import com.akerke.salonservice.infrastructure.kafka.KafkaProducer;
import com.akerke.salonservice.domain.mapper.MasterMapper;
import com.akerke.salonservice.domain.repository.MasterRepository;
import com.akerke.salonservice.domain.service.impl.MasterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MasterServiceTest {

    @InjectMocks
    private MasterServiceImpl masterService;
    @Mock
    private MasterRepository masterRepository;
    @Mock
    private MasterMapper masterMapper;
    @Mock
    private UserService userService;
    @Mock
    private TreatmentService treatmentService;
    @Mock
    private SalonService salonService;
    @Mock
    private KafkaProducer kafkaProducer;

    private Master master;
    private MasterDTO masterDTO;

    @BeforeEach
    void setUp() {
        masterDTO = new MasterDTO(1L, 1L, "Barber", new Date(), "some text");

        master = new Master();
        master.setId(1L);

        var user = new User();
        user.setId(1L);
        user.setEmail("email@gmail.com");

        var salon = new Salon();
        salon.setId(1L);

        master.setUser(user);
        master.setSalon(salon);
        master.setTreatments(new ArrayList<>());
    }

    @Test
    void delete_whenValidMasterId_thenDeleteMaster() {
        when(masterRepository.findById(master.getId())).thenReturn(Optional.of(master));

        masterService.delete(master.getId());

        verify(kafkaProducer).produce(
                AppConstants.MASTER_TOPIC_NAME,
                new KafkaManageRoleRequest(master.getSalonId(), master.getUser().getEmail(), false)
        );

        verify(masterRepository).delete(master);
    }

    @Test
    void delete_whenInvalidMasterId_thenThrowException() {
        when(masterRepository.findById(master.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> masterService.delete(master.getId()));

        verify(kafkaProducer, times(0)).produce(any(), any());
    }

    @Test
    void getAll_whenRepositoryReturnsEmptyList_thenReturnEmptyList() {
        when(masterRepository.findAll()).thenReturn(Collections.emptyList());

        var masters = masterService.getAll();

        assertThat(masters).hasSize(0);
    }

    @Test
    void getAll_whenRepositoryReturnsList_thenReturnSameList() {
        var masters = List.of(new Master(), new Master());
        when(masterRepository.findAll()).thenReturn(masters);

        var returnedMasters = masterService.getAll();

        assertThat(masters).isEqualTo(returnedMasters);
    }


    @Test
    void save_whenValidMasterDTO_thenReturnSavedMaster() {
        when(masterMapper.toModel(masterDTO)).thenReturn(master);
        when(userService.getById(master.getUserId())).thenReturn(master.getUser());
        when(salonService.getById(master.getSalonId())).thenReturn(master.getSalon());
        when(masterRepository.save(any(Master.class))).thenReturn(master);

        var savedMaster = masterService.save(masterDTO);

        assertThat(savedMaster).isEqualTo(master);

        verify(kafkaProducer).produce(
                AppConstants.MASTER_TOPIC_NAME,
                new KafkaManageRoleRequest(master.getSalonId(), master.getUser().getEmail(), true)
        );
        verify(masterMapper).toModel(masterDTO);
        verify(userService).getById(masterDTO.userId());
        verify(salonService).getById(masterDTO.salonId());
        verify(masterRepository).save(master);
        verify(kafkaProducer).produce(any(), any(KafkaManageRoleRequest.class));
    }

}