package com.akerke.salonservice.service;

public interface ManagerService {

    void add(Long userId, Long salonId);

    void remove(Long userId, Long salonId);

}
