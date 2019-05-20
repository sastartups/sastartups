package com.sastartup.demo.repositories;

import com.sastartup.demo.models.Job;
import com.sastartup.demo.models.Resume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends CrudRepository<Job,Long> {

}
