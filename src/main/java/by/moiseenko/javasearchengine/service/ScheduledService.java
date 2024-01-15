package by.moiseenko.javasearchengine.service;

/*
    @author Ilya Moiseenko on 12.01.24
*/

import by.moiseenko.javasearchengine.domain.Site;
import by.moiseenko.javasearchengine.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScheduledService {

    private final SiteService siteService;
    private final UserService userService;

    @Scheduled(fixedDelayString = "${scheduled.fixed.delay.millis}")
    public void scheduleIndexingSite() {
        log.info("scheduleIndexingSite start");

        List<User> users = userService.findAll();

        for (User user : users) {
            List<Site> userSites = user.getSites();

            for (Site site : userSites) {
                siteService.saveAndIndex(site);
            }
        }

        log.info("scheduleIndexingSite end");
    }
}
