package ru.jena.fuseki.sparqlconnector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FusekiQueryResult {
    public static final FusekiQueryResult DEFAULT = FusekiQueryResult.builder().build();

    @Builder.Default
    private FusekiQueryHeadResult head = FusekiQueryHeadResult.DEFAULT;
    @Builder.Default
    private FusekiQueryResultBindings results = FusekiQueryResultBindings.DEFAULT;
}
