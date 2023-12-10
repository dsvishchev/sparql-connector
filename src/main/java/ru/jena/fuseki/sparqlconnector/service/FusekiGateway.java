package ru.jena.fuseki.sparqlconnector.service;

import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.Model;
import ru.jena.fuseki.sparqlconnector.dto.FusekiQueryResult;

import java.io.File;

public interface FusekiGateway {
    void upload(String dataset, Model model);

    Model read(File rdf);

    Model get(String dataset);

    FusekiQueryResult query(String dataset, Query query);
}
