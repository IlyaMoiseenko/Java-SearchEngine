package by.moiseenko.javasearchengine.domain;

/*
    @author Ilya Moiseenko on 2.01.24
*/

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User role")
public enum Role {

    USER,
    ADMIN
}
