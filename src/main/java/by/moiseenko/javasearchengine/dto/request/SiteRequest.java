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

    @NotNull
    private String url;

    @NotNull
    private String name;
}
