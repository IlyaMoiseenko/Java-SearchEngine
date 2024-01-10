package by.moiseenko.javasearchengine.controller;

/*
    @author Ilya Moiseenko on 10.01.24
*/

import by.moiseenko.javasearchengine.dto.response.SiteResponse;
import by.moiseenko.javasearchengine.exception.SearchQueryException;
import by.moiseenko.javasearchengine.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final String QUERY_NULL_MESSAGE = "The search bar cannot be empty";

    private final SiteService siteService;

    @GetMapping
    public ResponseEntity<List<SiteResponse>> search(@RequestParam(value = "query", required = true) String query) {
        if (query == null)
            throw new SearchQueryException(QUERY_NULL_MESSAGE);

        return new ResponseEntity<>(
                siteService.searchByQuery(query),
                HttpStatus.OK
        );
    }
}
