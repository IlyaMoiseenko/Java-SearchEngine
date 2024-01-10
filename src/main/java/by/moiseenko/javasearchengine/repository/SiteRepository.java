package by.moiseenko.javasearchengine.repository;

/*
    @author Ilya Moiseenko on 3.01.24
*/


import by.moiseenko.javasearchengine.domain.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findByName(String name);

    @Query("select s from Site s join s.pages p join p.titles t where t.name = :query")
    List<Site> findAllByQuery(String query);
}
