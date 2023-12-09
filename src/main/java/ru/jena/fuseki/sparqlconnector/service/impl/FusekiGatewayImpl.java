package ru.jena.fuseki.sparqlconnector.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.stereotype.Service;
import ru.jena.fuseki.sparqlconnector.properties.FusekiProperties;
import ru.jena.fuseki.sparqlconnector.service.FusekiGateway;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FusekiGatewayImpl implements FusekiGateway {
    private static final String RDF_XML = "RDF/XML";
    private static final String SLASH = "/";

    private final FusekiProperties fusekiProperties;

    @Override
    public void upload(String dataset, Model model) {
        DatasetAccessorFactory
                .createHTTP(fusekiProperties.getUrl() + SLASH + dataset + "/data")
                .putModel(model);
    }

    @SneakyThrows
    @Override
    public Model read(File rdf) {
        var model = ModelFactory.createDefaultModel();
        try (var in = new BufferedInputStream(new FileInputStream(rdf))) {
            model.read(in, null, RDF_XML);
        }
        return model;
    }

    @SneakyThrows
    @Override
    public Model get(String dataset) {
        return DatasetAccessorFactory
                .createHTTP(fusekiProperties.getUrl() + SLASH + dataset)
                .getModel();
    }

    @Override
    public void query(String dataset, String query) {
        var service = fusekiProperties.getUrl() + SLASH + dataset;
        try (var execution = QueryExecutionFactory.sparqlService(service, query)) {
            var results = execution.execSelect();
            ResultSetFormatter.out(System.out, results);
            while (results.hasNext()) {
                var solution = results.nextSolution();
                System.out.println(solution);
            }
        }
        // TODO: посмотреть в сторону https://jena.apache.org/documentation/extras/querybuilder/
        // https://jena.apache.org/documentation/query/app_api.html
        // https://jena.apache.org/documentation/query/manipulating_sparql_using_arq.html
    }
}
