package ru.jena.fuseki.sparqlconnector.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ru.jena.fuseki")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FusekiProperties {
    public static final FusekiProperties LOCAL = FusekiProperties.builder().url("http://localhost:3030").build();

    @Builder.Default
    private String url = StringUtils.EMPTY;
}
