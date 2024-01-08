package by.moiseenko.javasearchengine.util;

import by.moiseenko.javasearchengine.config.JsoupConfig;
import by.moiseenko.javasearchengine.domain.Page;
import by.moiseenko.javasearchengine.domain.Site;
import by.moiseenko.javasearchengine.domain.Title;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.RecursiveTask;

/*
    @author Ilya Moiseenko on 8.01.24
*/

public class CreateMap extends RecursiveTask<List<Page>> {

    private final String url;
    private final Site site;
    private JsoupConfig jsoup = new JsoupConfig();

    public CreateMap(String url, Site site) {
        this.url = url;
        this.site = site;
    }

    @Override
    protected List<Page> compute() {

        // Создаём список ссылок
        List<Page> pages = Collections.synchronizedList(new ArrayList<>());

        // Создаём список объектов класса CreateMap
        List<CreateMap> tasks = new ArrayList<>();

        if (LinksKeeper.addLink(url)) {
            Page page = createPage(url, site);
            pages.add(page);

            Elements resultLinks = jsoup.getElements(url, "a[href]");

            try {
                if (!(resultLinks == null)) {
                    if (!(resultLinks.isEmpty())) {
                        //Создаём список для исключения циклического перебора ссылок
                        List<String> linksChildren = new ArrayList<>();
                        for (Element resultLink : resultLinks) {
                            String absLink = resultLink.attr("abs:href");
                            if ((!linksChildren.contains(absLink)) && absLink.startsWith(url)
                                    && !(absLink.contains("#")) && absLink.length() > url.length()) {
                                linksChildren.add(absLink);
                            }
                        }

                        //Запускаем потоки по отфильтрованному списку
                        for (String childLink : linksChildren) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                            }
                            CreateMap task = new CreateMap(childLink, site);
                            task.fork();
                            tasks.add(task);
                        }
                    }
                } else {
                    System.out.println("Нулевая ссылка : " + url);
                }

                for (CreateMap task : tasks) {
                    pages.addAll(task.join());
                }

            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }

        return pages;
    }

    private List<Title> findAllHeadersByPage(Page page) {
        List<Title> allHeaderByPage = new ArrayList<>();
        Elements headers = jsoup.getElements(page.getUrl(), "h1, h2, h3, h4, h5, h6");

        if (headers == null)
            return null;

        for (Element elementHeader : headers) {
            Title title = Title
                    .builder()
                    .name(elementHeader.text())
                    .page(page)
                    .build();

            allHeaderByPage.add(title);
        }

        return allHeaderByPage;
    }

    private Page createPage(String url, Site site) {
        Page page = Page
                .builder()
                .url(url)
                .site(site)
                .build();

        List<Title> allHeadersByUrl = findAllHeadersByPage(page);

        page.setTitles(allHeadersByUrl);

        return page;
    }
}
