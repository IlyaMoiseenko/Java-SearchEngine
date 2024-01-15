package by.moiseenko.javasearchengine.domain;

/*
    @author Ilya Moiseenko on 2.01.24
*/

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
@Schema(description = "User information")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Schema(description = "User first name")
    private String firstname;

    @Column(name = "last_name", nullable = false)
    @Schema(description = "User last name")
    private String lastname;

    @Column(name = "email", unique = true, nullable = false)
    @Schema(description = "User email")
    private String email;

    @Column(name = "password", nullable = false)
    @Schema(description = "User password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Schema(description = "User role")
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "owner")
    @Schema(description = "User sites")
    private List<Site> sites;
}
