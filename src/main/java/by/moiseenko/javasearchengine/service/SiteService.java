package by.moiseenko.javasearchengine.service;

/*
    @author Ilya Moiseenko on 3.01.24
*/

import by.moiseenko.javasearchengine.domain.IndexingStatus;
import by.moiseenko.javasearchengine.domain.Page;
import by.moiseenko.javasearchengine.domain.Site;
import by.moiseenko.javasearchengine.dto.response.SiteResponse;
import by.moiseenko.javasearchengine.exception.EntityNotFoundException;
import by.moiseenko.javasearchengine.mapper.SiteMapper;
import by.moiseenko.javasearchengine.repository.SiteRepository;
import by.moiseenko.javasearchengine.util.CreateMap;
import by.moiseenko.javasearchengine.util.LinksKeeper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SiteService {

    private final SiteRepository siteRepository;
    private final SiteMapper siteMapper;

    public Site saveAndIndex(Site site) {
        log.info("Start saving and indexing site");

        site.setIndexingStatus(IndexingStatus.NOT_INDEXED);
        Site savedSite = siteRepository.save(site);
        log.info("Site with name: " + savedSite.getName() + "was saved");

        if (savedSite != null) {
            setIndexingStatus(IndexingStatus.INDEXING, savedSite);

            // Запуск индексации сайта
            log.info("Indexing site start");
            Site indexedSite = indexing(savedSite);
            indexedSite.setIndexingStatus(IndexingStatus.INDEXED);
            log.info("Indexing site completed");

            log.info("Start save indexed site");
            siteRepository.save(indexedSite);
            log.info("Indexed site was saved");
        }

        return savedSite;
    }

    public Site findByName(String name) {
        return siteRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(Site.class, Map.of("Name", name)));
    }

    public List<SiteResponse> searchByQuery(String query) {
        List<Site> allByQuery = siteRepository.findAllByQuery(query.toLowerCase());

        return createSiteResponse(allByQuery);
    }

    private List<SiteResponse> createSiteResponse(List<Site> sites) {
        return sites.stream().map(siteMapper::siteToSiteResponse).toList();
    }

    public Site indexing(Site site) {
        // Создаем список для записи карты сайта
        List<Page> pages;

        CreateMap createMap = new CreateMap(site.getUrl(), site);
        ForkJoinPool pool = new ForkJoinPool();
        pages = pool.invoke(createMap);

        site.setPages(pages);
        LinksKeeper.linksSet = Collections.synchronizedSet(new HashSet<>());

        return site;
    }

    public void setIndexingStatus(IndexingStatus status, Site site) {
        site.setIndexingStatus(status);
        siteRepository.save(site);
    }
}
