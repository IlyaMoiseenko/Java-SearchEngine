package by.moiseenko.javasearchengine.repository;

/*
    @author Ilya Moiseenko on 3.01.24
*/


import by.moiseenko.javasearchengine.domain.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
}
