package by.moiseenko.javasearchengine.dto.error;

/*
    @author Ilya Moiseenko on 9.01.24
*/

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationError extends SubError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
