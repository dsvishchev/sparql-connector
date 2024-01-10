package ru.jena.fuseki.sparqlconnector.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.jena.fuseki.sparqlconnector.constants.QueryTypeConstants;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SelectFusekiQueryDto.class, name = QueryTypeConstants.SELECT)
})
public interface FusekiQueryDto {
    String getType();
}
