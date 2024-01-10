package ru.jena.fuseki.sparqlconnector.transformer;

import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.query.Query;
import org.springframework.stereotype.Component;
import ru.jena.fuseki.sparqlconnector.model.FusekiQuery;
import ru.jena.fuseki.sparqlconnector.model.SelectFusekiQuery;

@Component
public class SelectQueryTransformer {

    public Query transform(FusekiQuery query) {
        var select = (SelectFusekiQuery) query;
        var builder = new SelectBuilder();

        select.getVariables().forEach(builder::addVar);
        return builder.addWhere(select.getSubject(), select.getPredicate(), select.getObject())
                .build();
    }
}
