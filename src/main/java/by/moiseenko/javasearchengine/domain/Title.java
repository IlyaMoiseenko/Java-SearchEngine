package by.moiseenko.javasearchengine.domain;

/*
    @author Ilya Moiseenko on 8.01.24
*/

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_title")
@Schema(description = "Title information")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Title id")
    private Long id;

    @Column(name = "name")
    @Schema(description = "Title name")
    private String name;

    @ManyToOne
    @Schema(description = "The page where the title is located")
    private Page page;
}
