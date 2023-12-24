package ru.jena.fuseki.sparqlconnector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FusekiQueryResultObject {
    private String type;
    private String value;
}
