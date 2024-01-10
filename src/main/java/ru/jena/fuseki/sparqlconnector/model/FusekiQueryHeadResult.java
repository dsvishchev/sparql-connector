package ru.jena.fuseki.sparqlconnector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FusekiQueryHeadResult {
    public static final FusekiQueryHeadResult DEFAULT = FusekiQueryHeadResult.builder().build();

    @Builder.Default
    private List<String> vars = List.of();
}
