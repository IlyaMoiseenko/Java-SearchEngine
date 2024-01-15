package by.moiseenko.javasearchengine.domain;

/*
    @author Ilya Moiseenko on 9.01.24
*/

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Site indexing status")
public enum IndexingStatus {
    INDEXING, INDEXED, NOT_INDEXED
}
