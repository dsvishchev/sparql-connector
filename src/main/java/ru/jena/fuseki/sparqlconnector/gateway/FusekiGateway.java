package ru.jena.fuseki.sparqlconnector.gateway;

import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.Model;
import ru.jena.fuseki.sparqlconnector.model.FusekiQueryResult;

import java.io.File;

public interface FusekiGateway {
    void upload(String dataset, Model model);

    Model read(File rdf);

    Model read(byte[] bytes);

    Model getModel(String dataset);

    FusekiQueryResult query(String dataset, Query query);
}
