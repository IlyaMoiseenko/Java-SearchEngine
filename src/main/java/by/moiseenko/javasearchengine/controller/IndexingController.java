package by.moiseenko.javasearchengine.controller;

/*
    @author Ilya Moiseenko on 12.01.24
*/

import by.moiseenko.javasearchengine.domain.Site;
import by.moiseenko.javasearchengine.domain.User;
import by.moiseenko.javasearchengine.domain.UserPrincipal;
import by.moiseenko.javasearchengine.dto.response.SiteResponse;
import by.moiseenko.javasearchengine.mapper.SiteMapper;
import by.moiseenko.javasearchengine.repository.SiteRepository;
import by.moiseenko.javasearchengine.service.SiteService;
import by.moiseenko.javasearchengine.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/indexing")
@RequiredArgsConstructor
@Tag(name = "Indexing controller", description = "Controller for indexing site")
public class IndexingController {

    private final SiteMapper siteMapper;

    private final SiteService siteService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "The method for starting site indexing")
    public ResponseEntity<List<SiteResponse>> indexing(
            @Parameter(description = "The user whose sites will be indexed") @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        List<SiteResponse> response = new ArrayList<>();

        User owner = userService.findByEmail(userPrincipal.getEmail());
        List<Site> sites = owner.getSites();

        for (Site site : sites) {
            //siteRepository.delete(site);
            Site indexedSite = siteService.saveAndIndex(site);
            response.add(
                    siteMapper.siteToSiteResponse(indexedSite)
            );
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
