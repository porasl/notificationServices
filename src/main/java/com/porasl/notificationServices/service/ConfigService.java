package com.porasl.notificationServices.service;

import java.util.List;

import com.porasl.notificationServices.config.Config;

public interface ConfigService {

//    public NotificationMessage sendNotification(NotificationMessage message) {
//        return new NotificationMessage("Message from porasl.com, " + message.getContent() + "!");
//    }
    
    void save(Config config);
    List<Config> findAll();
    void update(Config config);
    void delete(Config config);
}
