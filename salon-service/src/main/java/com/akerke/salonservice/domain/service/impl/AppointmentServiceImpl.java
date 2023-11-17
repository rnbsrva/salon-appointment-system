package com.akerke.salonservice.domain.service.impl;

import com.akerke.salonservice.common.constants.AppConstants;
import com.akerke.salonservice.common.constants.NotificationType;
import com.akerke.salonservice.domain.dto.AppointmentDTO;
import com.akerke.salonservice.domain.entity.Appointment;
import com.akerke.salonservice.common.exception.EntityNotFoundException;
import com.akerke.salonservice.domain.repository.AppointmentRepository;
import com.akerke.salonservice.domain.service.*;
import com.akerke.salonservice.domain.mapper.AppointmentMapper;
import com.akerke.salonservice.infrastructure.kafka.KafkaProducer;
import com.akerke.salonservice.infrastructure.kafka.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.akerke.salonservice.common.constants.NotificationType.*;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserService userService;
    private final MasterService masterService;
    private final TreatmentService treatmentService;
    private final WorkTimeService workTimeService;
    private final KafkaProducer kafkaProducer;


    @Override
    public Appointment save(AppointmentDTO appointmentDTO) {

        var user = userService.getById(appointmentDTO.userId());
        var master = masterService.getById(appointmentDTO.masterId());

        var appointment = appointmentMapper.toModel(appointmentDTO);
        appointment.setUser(user);
        appointment.setMaster(master);
        appointment.setTreatment(treatmentService.getById(appointmentDTO.treatmentId()));
        appointment.setWorkTime(workTimeService.getById(appointmentDTO.workTimeId()) );

        var savedAppointment = appointmentRepository.save(appointment);

        System.out.println(savedAppointment.toString());

        kafkaProducer.produce(AppConstants.NOTIFY_TOPIC_NAME,
                new NotificationDTO(
                        user.getId(),
                        "New Appointment",
                         writeNotifyMsg(savedAppointment, APPOINTMENT_CONFIRMATION, "user"),
                        APPOINTMENT_CONFIRMATION,
                        user.getPhone()));
        kafkaProducer.produce(AppConstants.NOTIFY_TOPIC_NAME,
                new NotificationDTO(
                        appointment.getMasterId(),
                        "New Appointment",
                        writeNotifyMsg(appointment, APPOINTMENT_CONFIRMATION, "master"),
                        APPOINTMENT_CONFIRMATION,
                        appointment.getMaster().getUser().getPhone()));

        return savedAppointment;
    }

    @Override
    public void update(AppointmentDTO appointmentDTO, Long id) {
        var appointment = this.getById(id);
        appointmentMapper.update(appointmentDTO, appointment);
        appointmentRepository.save(appointment);

        kafkaProducer.produce(AppConstants.NOTIFY_TOPIC_NAME,
                new NotificationDTO(
                        appointment.getUser().getId(),
                        "Appointment rescheduled",
                        writeNotifyMsg(appointment, APPOINTMENT_RESCHEDULE, "user"),
                        APPOINTMENT_RESCHEDULE,
                        appointment.getUser().getPhone()));
        kafkaProducer.produce(AppConstants.NOTIFY_TOPIC_NAME,
                new NotificationDTO(
                        appointment.getMasterId(),
                        "Appointment rescheduled",
                        writeNotifyMsg(appointment, APPOINTMENT_RESCHEDULE, "master"),
                        APPOINTMENT_RESCHEDULE,
                        appointment.getMaster().getUser().getPhone()));
    }

    @Override
    public void delete(Long id) {
        Appointment appointment = this.getById(id);

        kafkaProducer.produce(AppConstants.NOTIFY_TOPIC_NAME,
                new NotificationDTO(
                        appointment.getUser().getId(),
                        "Appointment cancelled",
                        writeNotifyMsg(appointment, APPOINTMENT_CANCELLATION, "user"),
                        APPOINTMENT_CANCELLATION,
                        appointment.getUser().getPhone()));

        kafkaProducer.produce(AppConstants.NOTIFY_TOPIC_NAME,
                new NotificationDTO(
                        appointment.getMasterId(),
                        "Appointment cancelled",
                        writeNotifyMsg(appointment, APPOINTMENT_CANCELLATION, "master"),
                        APPOINTMENT_CANCELLATION,
                        appointment.getMaster().getUser().getPhone()));


        appointmentRepository.delete(appointment);
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(Appointment.class, id));
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }



    private static String writeNotifyMsg(Appointment appointment, NotificationType notificationType, String target) {
        String subtitle = getSubtitle(notificationType);

        StringBuilder sb = new StringBuilder();
        if (Objects.equals(target, "user")) {
            sb.append(appointment.getUser().getName()+subtitle).append("\n");
            sb.append("Master: ").append(appointment.getMaster().getUser().getName()).append("\n");
        }
        else {
            sb.append(appointment.getMaster().getUser().getName()+subtitle).append("\n");
            sb.append("Client: ").append(appointment.getUser().getName()).append("\n");
        }
        sb.append("Date: ").append(appointment.getWorkTime()).append("\n");
        sb.append("Salon: ").append(appointment.getMaster().getSalon().getName()).append("\n");

        sb.append("Time: ").append(appointment.getWorkTime().getStartTime()).append("\n");
        sb.append("Service: ").append(appointment.getTreatment().getName()).append("\n");

        sb.append("\nYou will be able to create, edit, or cancel your entries on the site");

        return sb.toString();
    }

    private static String getSubtitle(NotificationType notificationType) {
        String subtitle = "";

        switch (notificationType) {
            case APPOINTMENT_CONFIRMATION -> subtitle = ", we confirm the entry:\n";
            case APPOINTMENT_REMINDER -> subtitle = ", a reminder for your appointment:\n";
            case APPOINTMENT_CANCELLATION -> subtitle = ", appointment cancellation:\n";
            case APPOINTMENT_RESCHEDULE -> subtitle = ", appointment reschedule:\n";
            default -> {
            }
        }
        return subtitle;
    }
}
