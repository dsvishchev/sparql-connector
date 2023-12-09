package ru.jena.fuseki.sparqlconnector.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jena.fuseki.sparqlconnector.properties.FusekiProperties;
import ru.jena.fuseki.sparqlconnector.service.impl.FusekiGatewayImpl;

import java.io.File;
import java.util.Objects;

class FusekiGatewayImplTest {
    private static final FusekiGateway FUSEKI_GATEWAY = new FusekiGatewayImpl(FusekiProperties.LOCAL);
    private static final String RESOURCE = "book-dataset.rdf";
    private static final String DATASET = "ds";

    @Test
    void upload() {
        //given:
        var classLoader = getClass().getClassLoader();
        var file = new File(Objects.requireNonNull(classLoader.getResource(RESOURCE)).getFile());
        var model = FUSEKI_GATEWAY.read(file);

        //expect:
        Assertions.assertDoesNotThrow(() -> FUSEKI_GATEWAY.upload(DATASET, model));
    }

    @SneakyThrows
    @Test
    void get() {
        //given:
        var classLoader = getClass().getClassLoader();
        var file = new File(Objects.requireNonNull(classLoader.getResource(RESOURCE)).getFile());
        var expected = FUSEKI_GATEWAY.read(file);
        FUSEKI_GATEWAY.upload(DATASET, expected);

        //when:
        var actual = FUSEKI_GATEWAY.get(DATASET);

        //then:
        Assertions.assertEquals(expected.getGraph().toString(), actual.getGraph().toString());

        /*StringWriter out = new StringWriter();
        model.write(out, RDF_XML);
        return out.toString().getBytes(StandardCharsets.UTF_8);*/
    }

    @Test
    void query() {
        //given:
        var classLoader = getClass().getClassLoader();
        var file = new File(Objects.requireNonNull(classLoader.getResource(RESOURCE)).getFile());
        var expected = FUSEKI_GATEWAY.read(file);
        FUSEKI_GATEWAY.upload(DATASET, expected);

        //when:
        FUSEKI_GATEWAY.query(DATASET, "SELECT * {?s ?p ?o}");

        //then:
        System.out.println("success");
    }
}
