package ru.jena.fuseki.sparqlconnector.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import ru.jena.fuseki.sparqlconnector.gateway.FusekiGateway;
import ru.jena.fuseki.sparqlconnector.model.FusekiQuery;
import ru.jena.fuseki.sparqlconnector.model.FusekiQueryResult;
import ru.jena.fuseki.sparqlconnector.model.FusekiQueryResultObject;
import ru.jena.fuseki.sparqlconnector.transformer.SelectQueryTransformer;
import ru.jena.fuseki.sparqlconnector.utils.FusekiUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static ru.jena.fuseki.sparqlconnector.utils.FusekiUtils.RDF_XML;

@Slf4j
@Service
@RequiredArgsConstructor
public class SparqlConnectorServiceImpl {
    private final FusekiGateway gateway;
    private final SelectQueryTransformer transformer;

    public void upload(String dataset, byte[] bytes) {
        var model = FusekiUtils.read(bytes);
        gateway.upload(dataset, model);
    }

    @SneakyThrows
    public byte[] getBytes(String dataset) {
        var model = gateway.getModel(dataset);
        byte[] result;
        try (StringWriter out = new StringWriter()) {
            model.write(out, RDF_XML);
            result = out.toString().getBytes(StandardCharsets.UTF_8);
        }
        return result;
    }

    public FusekiQueryResult query(String dataset, FusekiQuery query) {
        var fusekiQuery = transformer.transform(query);
        return gateway.query(dataset, fusekiQuery);
    }

    @SneakyThrows
    public byte[] queryAsCsv(String dataset, FusekiQuery query) {
        var queryResult = query(dataset, query);
        byte[] result;
        var csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(queryResult.getHead().getVars().toArray(new String[0]))
                .build();
        try (StringWriter sw = new StringWriter();
             CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            queryResult.getResults().getBindings().stream()
                    .map(entry -> entry.values().stream()
                            .map(FusekiQueryResultObject::getValue)
                            .collect(Collectors.toList())
                    )
                    .forEach(values -> {
                        try {
                            printer.printRecord(values);
                        } catch (IOException e) {
                            log.error("Error during create csv record", e);
                            throw new RuntimeException(e);
                        }
                    });
            result = sw.toString().getBytes(StandardCharsets.UTF_8);
        }
        return result;
    }
}
