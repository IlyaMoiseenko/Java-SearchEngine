package by.moiseenko.javasearchengine.dto.request;

/*
    @author Ilya Moiseenko on 3.01.24
*/

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteRequest {

    @NotNull(message = "The field should not be empty")
    private String url;

    @NotNull(message = "The field should not be empty")
    private String name;
}
