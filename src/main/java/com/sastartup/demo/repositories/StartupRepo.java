package com.sastartup.demo.repositories;

import com.sastartup.demo.models.Resume;
import com.sastartup.demo.models.Startup;
import com.sastartup.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartupRepo extends CrudRepository<Startup, Long> {
}