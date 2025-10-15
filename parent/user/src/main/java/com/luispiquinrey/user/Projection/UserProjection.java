package com.luispiquinrey.user.Projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import com.luispiquinrey.Service.FacadeServiceWithRedis;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Event.UserCreatedEvent;
import com.luispiquinrey.user.Service.ServiceUser;
@Component
public class UserProjection {
    private FacadeServiceWithRedis facadeServiceWithRedis;

    @Autowired
    public UserProjection(FacadeServiceWithRedis facadeServiceWithRedis){
        this.facadeServiceWithRedis=facadeServiceWithRedis;
    }

    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent){
        Contact contact=new Contact();
        BeanUtils.copyProperties(userCreatedEvent, contact);
        facadeServiceWithRedis.createTarget(contact);
    }
}
