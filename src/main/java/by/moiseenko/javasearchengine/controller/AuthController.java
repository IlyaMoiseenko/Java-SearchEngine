package by.moiseenko.javasearchengine.controller;

import by.moiseenko.javasearchengine.domain.User;
import by.moiseenko.javasearchengine.dto.request.AuthenticationRequest;
import by.moiseenko.javasearchengine.dto.request.RegisterRequest;
import by.moiseenko.javasearchengine.dto.response.AuthenticationResponse;
import by.moiseenko.javasearchengine.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    @author Ilya Moiseenko on 2.01.24
*/

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@Tag(name = "Authentication controller", description = "The controller for authorization and authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Method for register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Method for login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }
}
