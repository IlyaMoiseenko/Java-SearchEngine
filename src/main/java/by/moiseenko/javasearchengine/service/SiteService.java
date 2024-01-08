package by.moiseenko.javasearchengine.service;

/*
    @author Ilya Moiseenko on 3.01.24
*/

import by.moiseenko.javasearchengine.domain.Page;
import by.moiseenko.javasearchengine.domain.Site;
import by.moiseenko.javasearchengine.repository.PageRepository;
import by.moiseenko.javasearchengine.repository.SiteRepository;
import by.moiseenko.javasearchengine.util.CreateMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;

    public Site save(Site site) {
        Site savedSite = siteRepository.save(site);

        if (savedSite != null) {
            List<Page> pages = indexing(savedSite);
            pageRepository.saveAll(pages);
        }

        return savedSite;
    }

    private List<Page> indexing(Site site) {
        // Создаем список для записи карты сайта
        List<Page> pages;

        CreateMap createMap = new CreateMap(site.getUrl(), site);
        ForkJoinPool pool = new ForkJoinPool();
        pages = pool.invoke(createMap);

        return pages;
    }
}
