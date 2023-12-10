package ru.jena.fuseki.sparqlconnector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FusekiQueryHeadResult {
    public static final FusekiQueryHeadResult DEFAULT = FusekiQueryHeadResult.builder().build();

    @Builder.Default
    private Set<String> vars = Set.of();
}
