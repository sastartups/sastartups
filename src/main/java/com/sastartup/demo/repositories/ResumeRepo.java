package com.sastartup.demo.repositories;

import com.sastartup.demo.models.Resume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepo extends CrudRepository<Resume, Long> {
}
