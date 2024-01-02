package by.moiseenko.javasearchengine.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    @author Ilya Moiseenko on 2.01.24
*/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "This field should not be null")
    private String firstname;

    @NotNull(message = "This field should not be null")
    private String lastname;

    @NotNull(message = "This field should not be null")
    @Email
    private String email;

    @NotNull(message = "This field should not be null")
    private String password;
}