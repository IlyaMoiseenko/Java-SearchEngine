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

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_page")
@Schema(description = "Page information")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Page id")
    private Long id;

    @Column(name = "url", nullable = false)
    @Schema(description = "Page url")
    private String url;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    @Schema(description = "Page titles")
    private List<Title> titles;

    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    @Schema(description = "The site the page belongs to")
    private Site site;
}
