package by.moiseenko.javasearchengine.exception;

/*
    @author Ilya Moiseenko on 9.01.24
*/

import org.springframework.util.StringUtils;

import java.util.Map;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, Map<String, String> searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), searchParamsMap));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                searchParams;
    }
}
