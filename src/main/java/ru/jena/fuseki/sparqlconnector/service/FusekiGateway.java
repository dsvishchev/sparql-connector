package ru.jena.fuseki.sparqlconnector.service;

import org.apache.jena.rdf.model.Model;

import java.io.File;

public interface FusekiGateway {
    void upload(String dataset, Model model);

    Model read(File rdf);

    Model get(String dataset);

    void query(String dataset, String query);
}
