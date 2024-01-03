package by.moiseenko.javasearchengine.service;

/*
    @author Ilya Moiseenko on 3.01.24
*/

import by.moiseenko.javasearchengine.domain.Site;
import by.moiseenko.javasearchengine.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;

    public Site save(Site site) {
        return siteRepository.save(site);
    }
}
