package com.sastartup.demo.repositories;

import com.sastartup.demo.models.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends CrudRepository<Job,Long> {
}
