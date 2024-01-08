package by.moiseenko.javasearchengine.repository;

import by.moiseenko.javasearchengine.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    @author Ilya Moiseenko on 8.01.24
*/
public interface PageRepository extends JpaRepository<Page, Long> {
}
