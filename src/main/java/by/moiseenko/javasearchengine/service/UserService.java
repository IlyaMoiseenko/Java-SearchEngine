package by.moiseenko.javasearchengine.service;

/*
    @author Ilya Moiseenko on 3.01.24
*/

import by.moiseenko.javasearchengine.domain.User;
import by.moiseenko.javasearchengine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
