package com.sastartup.demo.repositories;

import com.sastartup.demo.models.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepo extends CrudRepository<Application,Long> {
}
