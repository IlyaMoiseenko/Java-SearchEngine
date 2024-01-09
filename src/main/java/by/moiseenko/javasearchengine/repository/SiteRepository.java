package by.moiseenko.javasearchengine.repository;

/*
    @author Ilya Moiseenko on 3.01.24
*/


import by.moiseenko.javasearchengine.domain.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findByName(String name);
}
