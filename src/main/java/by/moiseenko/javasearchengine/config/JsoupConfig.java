package by.moiseenko.javasearchengine.config;

/*
    @author Ilya Moiseenko on 9.01.24
*/

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@Getter
public class JsoupConfig {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String REFERRER = "http://www.google.com";
    private final int TIMEOUT = 10000;

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent(this.USER_AGENT)
                //.referrer(this.REFERRER)
                .timeout(this.TIMEOUT)
                .get();
    }

    private Elements getElements(Document document, String cssQuery) {
        return document.select(cssQuery);
    }

    public Elements getElements(String pageUrl, String cssQuery) {
        try {
            Document document = getDocument(pageUrl);
            Elements elements = getElements(document, cssQuery);

            return elements;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
