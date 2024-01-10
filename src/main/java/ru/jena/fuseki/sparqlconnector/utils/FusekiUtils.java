package ru.jena.fuseki.sparqlconnector.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FusekiUtils {
    public static final String RDF_XML = "RDF/XML";
    public static final String SLASH = "/";

    @SneakyThrows
    public static Model read(File rdf) {
        var model = ModelFactory.createDefaultModel();
        try (var in = new BufferedInputStream(new FileInputStream(rdf))) {
            model.read(in, null, RDF_XML);
        }
        return model;
    }

    @SneakyThrows
    public static Model read(byte[] bytes) {
        var model = ModelFactory.createDefaultModel();
        try (var in = new BufferedInputStream(new ByteArrayInputStream(bytes))) {
            model.read(in, null, RDF_XML);
        }
        return model;
    }
}
