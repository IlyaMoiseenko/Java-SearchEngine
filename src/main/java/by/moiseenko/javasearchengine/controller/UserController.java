package by.moiseenko.javasearchengine.controller;

/*
    @author Ilya Moiseenko on 3.01.24
*/

import by.moiseenko.javasearchengine.domain.Site;
import by.moiseenko.javasearchengine.domain.UserPrincipal;
import by.moiseenko.javasearchengine.dto.request.SiteRequest;
import by.moiseenko.javasearchengine.dto.response.SiteResponse;
import by.moiseenko.javasearchengine.mapper.SiteMapper;
import by.moiseenko.javasearchengine.service.SiteService;
import by.moiseenko.javasearchengine.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final SiteMapper siteMapper;
    private final UserService userService;
    private final SiteService siteService;

    @PostMapping("/site")
    public ResponseEntity<SiteResponse> siteToIndex(@RequestBody @Valid SiteRequest siteToIndexing,
                                                    @AuthenticationPrincipal UserPrincipal owner) {
        Site site = siteMapper.siteRequestToSite(siteToIndexing);
        site.setOwner(
                userService.findByEmail(owner.getEmail())
        );

        Site savedSiteToIndexing = siteService.save(site);

        return new ResponseEntity<>(
                siteMapper.siteToSiteResponse(savedSiteToIndexing),
                HttpStatus.OK
        );
    }
}
