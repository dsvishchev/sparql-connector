package ru.jena.fuseki.sparqlconnector.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;
import ru.jena.fuseki.sparqlconnector.dto.FusekiQueryDto;
import ru.jena.fuseki.sparqlconnector.dto.SelectFusekiQueryDto;
import ru.jena.fuseki.sparqlconnector.model.FusekiQuery;
import ru.jena.fuseki.sparqlconnector.model.SelectFusekiQuery;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FusekiQueryConverter {

    default FusekiQuery convert(FusekiQueryDto source) {
        if (source instanceof SelectFusekiQueryDto) {
            return convert((SelectFusekiQueryDto) source);
        }
        return null;
    }

    SelectFusekiQuery convert(SelectFusekiQueryDto source);
}
