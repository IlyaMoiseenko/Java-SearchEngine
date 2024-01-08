package by.moiseenko.javasearchengine.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
    @author Ilya Moiseenko on 8.01.24
*/

@Getter
@Component
public class LinksKeeper {

    // Создаём Set для списка уникальныъх ссылок
    public static Set<String> linksSet = Collections.synchronizedSet(new HashSet<>());

    public static boolean addLink(String link){
        return linksSet.add(link);
    }
}
