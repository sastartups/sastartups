package com.sastartup.demo.repositories;


import com.sastartup.demo.models.Notification;
import com.sastartup.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends CrudRepository<Notification,Long> {
    List<Notification> findByUserOrderByIdDesc(User user);
    List<Notification> findTop4ByUserOrderByIdDesc(User user);
}
