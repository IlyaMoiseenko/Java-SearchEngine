package by.moiseenko.javasearchengine.service;

import by.moiseenko.javasearchengine.config.JwtService;
import by.moiseenko.javasearchengine.domain.Role;
import by.moiseenko.javasearchengine.domain.User;
import by.moiseenko.javasearchengine.domain.UserPrincipal;
import by.moiseenko.javasearchengine.dto.request.AuthenticationRequest;
import by.moiseenko.javasearchengine.dto.request.RegisterRequest;
import by.moiseenko.javasearchengine.dto.response.AuthenticationResponse;
import by.moiseenko.javasearchengine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    @author Ilya Moiseenko on 2.01.24
*/

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private String TOKEN;

    public User register(RegisterRequest request) {
        User userToRegister = User
                .builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(userToRegister);

        return userToRegister;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            UserPrincipal userPrincipal = UserPrincipal
                    .builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .build();

            TOKEN = jwtService.generateToken(userPrincipal);
        }

        return AuthenticationResponse
                .builder()
                .token(TOKEN)
                .build();
    }
}
