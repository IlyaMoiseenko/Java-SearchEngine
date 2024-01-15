package by.moiseenko.javasearchengine.domain;

/*
    @author Ilya Moiseenko on 3.01.24
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
@Table(name = "tb_site")
@Schema(description = "Site information")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Site id")
    private Long id;

    @Column(name = "url", nullable = false, unique = true)
    @Schema(description = "Site id")
    private String url;

    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Site name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Schema(description = "Site owner")
    private User owner;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    @Schema(description = "Site pages")
    private List<Page> pages;

    @Enumerated(EnumType.STRING)
    @Column(name = "indexing_status")
    @Schema(description = "Site indexing status")
    private IndexingStatus indexingStatus;
}
