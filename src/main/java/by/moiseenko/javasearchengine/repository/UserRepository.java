package by.moiseenko.javasearchengine.repository;

/*
    @author Ilya Moiseenko on 2.01.24
*/

import by.moiseenko.javasearchengine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
