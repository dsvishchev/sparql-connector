package ru.jena.fuseki.sparqlconnector.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.stereotype.Service;
import ru.jena.fuseki.sparqlconnector.dto.FusekiQueryResult;
import ru.jena.fuseki.sparqlconnector.properties.FusekiProperties;
import ru.jena.fuseki.sparqlconnector.service.FusekiGateway;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FusekiGatewayImpl implements FusekiGateway {
    private static final String RDF_XML = "RDF/XML";
    private static final String SLASH = "/";

    private final FusekiProperties fusekiProperties;
    private final ObjectMapper mapper;

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

    @SneakyThrows
    @Override
    public FusekiQueryResult query(String dataset, Query query) {
        var service = fusekiProperties.getUrl() + SLASH + dataset;
        FusekiQueryResult result;
        try (var execution = QueryExecutionFactory.sparqlService(service, query)) {
            var results = execution.execSelect();
            try (var bos = new ByteArrayOutputStream()) {
                ResultSetFormatter.outputAsJSON(bos, results);
                result = mapper.readValue(bos.toByteArray(), FusekiQueryResult.class);
            }
        }
        return result;
    }
}