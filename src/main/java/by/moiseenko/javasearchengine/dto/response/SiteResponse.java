package by.moiseenko.javasearchengine.dto.response;

/*
    @author Ilya Moiseenko on 3.01.24
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteResponse {

    private String name;
}
