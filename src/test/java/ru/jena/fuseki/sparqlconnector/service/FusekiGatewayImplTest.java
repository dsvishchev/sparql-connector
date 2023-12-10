package ru.jena.fuseki.sparqlconnector.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jena.fuseki.sparqlconnector.properties.FusekiProperties;
import ru.jena.fuseki.sparqlconnector.service.impl.FusekiGatewayImpl;

import java.io.File;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FusekiGatewayImplTest {
    private static final FusekiGateway FUSEKI_GATEWAY = new FusekiGatewayImpl(FusekiProperties.LOCAL, new ObjectMapper());
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
        var query = new SelectBuilder()
                .addVar("*")
                .addWhere("?s", "?p", "?o")
                .build();

        //when:
        var result = FUSEKI_GATEWAY.query(DATASET, query);

        //then:
        Assertions.assertEquals(Set.of("s", "p", "o"), result.getHead().getVars());
        Assertions.assertEquals(4, result.getResults().getBindings().size());
        assertThat(result.getHead().getVars())
                .hasSize(3)
                .areExactly(1, varCondition("s"))
                .areExactly(1, varCondition("p"))
                .areExactly(1, varCondition("o"));
        assertThat(result.getResults().getBindings())
                .hasSize(4);
    }

    private Condition<String> varCondition(String expected) {
        return new Condition<>(expected::equals, "var condition");
    }
}
