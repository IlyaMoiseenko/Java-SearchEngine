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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "The controller for working with the site through the user")
public class UserController {

    private final SiteMapper siteMapper;
    private final UserService userService;
    private final SiteService siteService;

    @PostMapping("/site")
    @Operation(summary = "A method for saving and indexing a site")
    public ResponseEntity<SiteResponse> siteToIndex(
            @Parameter(description = "Site for indexing")
            @RequestBody @Valid SiteRequest siteToIndexing,

            @Parameter(description = "Authenticated user (site owner)")
            @AuthenticationPrincipal UserPrincipal owner
    ) {
        Site site = siteMapper.siteRequestToSite(siteToIndexing);
        site.setOwner(
                userService.findByEmail(owner.getEmail())
        );

        Site savedSiteToIndexing = siteService.saveAndIndex(site);

        return new ResponseEntity<>(
                siteMapper.siteToSiteResponse(savedSiteToIndexing),
                HttpStatus.OK
        );
    }

    @GetMapping("/site")
    @Operation(summary = "Method for find site by name")
    public ResponseEntity<SiteResponse> findByNameParam(
            @Parameter(description = "Site name for find site")
            @RequestParam(value = "name") String name
    ) {
        Site site = siteService.findByName(name);

        return new ResponseEntity<>(
                siteMapper.siteToSiteResponse(site),
                HttpStatus.OK
        );
    }
}
