package com.sastartup.demo.repositories;

import com.sastartup.demo.models.Resume;
import com.sastartup.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepo extends CrudRepository<Resume, Long> {

    List<Resume>findByOwner(User user);
    Resume findByOwnerId(Long id);


}
