package by.moiseenko.javasearchengine.mapper;

/*
    @author Ilya Moiseenko on 3.01.24
*/

import by.moiseenko.javasearchengine.domain.Site;
import by.moiseenko.javasearchengine.dto.request.SiteRequest;
import by.moiseenko.javasearchengine.dto.response.SiteResponse;
import org.springframework.stereotype.Component;

@Component
public class SiteMapper {

    public Site siteRequestToSite(SiteRequest siteRequest) {
        return Site
                .builder()
                .url(siteRequest.getUrl())
                .name(siteRequest.getName())
                .build();
    }

    public SiteResponse siteToSiteResponse(Site site) {
        return SiteResponse
                .builder()
                .name(site.getName())
                .indexingStatus(site.getIndexingStatus())
                .build();
    }
}
