package ru.jena.fuseki.sparqlconnector.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointConstants {
    public static final String EXTERNAL = "/external";
    public static final String V1 = "/v1";

    public static final String JENA_FUSEKI = "/jena";
    public static final String BASE = EXTERNAL + V1 + JENA_FUSEKI;
}
