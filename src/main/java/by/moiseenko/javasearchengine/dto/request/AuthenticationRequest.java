package by.moiseenko.javasearchengine.dto.request;

/*
    @author Ilya Moiseenko on 2.01.24
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "This field should not be null")
    @Email
    private String email;

    @NotNull(message = "This field should not be null")
    private String password;
}
