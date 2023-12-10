package ru.jena.fuseki.sparqlconnector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FusekiQueryResultBindings {
    public static final FusekiQueryResultBindings DEFAULT = FusekiQueryResultBindings.builder().build();

    @Builder.Default
    private List<HashMap<String, Object>> bindings = List.of();
}
