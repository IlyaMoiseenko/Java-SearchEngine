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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;
    private final SiteMapper siteMapper;

    public Site save(Site site) {
        site.setIndexingStatus(IndexingStatus.NOT_INDEXED);
        Site savedSite = siteRepository.save(site);

        if (savedSite != null) {

            // Установка статуса "Индексируется"
            setIndexingStatus(IndexingStatus.INDEXING, savedSite);

            // Запуск индексации сайта
            Site indexedSite = indexing(savedSite);

            // Установка статуса "Проиндексирован"
            setIndexingStatus(IndexingStatus.INDEXED, indexedSite);

            siteRepository.save(indexedSite);
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

    private Site indexing(Site site) {
        // Создаем список для записи карты сайта
        List<Page> pages;

        CreateMap createMap = new CreateMap(site.getUrl(), site);
        ForkJoinPool pool = new ForkJoinPool();
        pages = pool.invoke(createMap);

        site.setPages(pages);

        return site;
    }

    private void setIndexingStatus(IndexingStatus status, Site site) {
        site.setIndexingStatus(status);
        siteRepository.save(site);
    }
}
