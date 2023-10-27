package com.akerke.chatservice.service;


public interface UserStatusService {

    void setOnline(Long applicationId);

    void setOffline(Long applicationId);

    Boolean isOnline(Long applicationId);

    void setOnline(Long salonId, Long applicationId);

    void setOffline(Long salonId, Long applicationId);

    Boolean supportChatIsOnline(Long salonId);

}
