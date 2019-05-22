package com.sastartup.demo.repositories;


import com.sastartup.demo.models.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepo extends CrudRepository<Notification,Long> {
}
