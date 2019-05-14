package com.sastartup.demo.repositories;

        import com.sastartup.demo.models.User;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {

}
