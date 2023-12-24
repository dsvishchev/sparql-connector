package ru.jena.fuseki.sparqlconnector.api.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.jena.fuseki.sparqlconnector.api.SparqlConnectorController;
import ru.jena.fuseki.sparqlconnector.converter.FusekiQueryConverter;
import ru.jena.fuseki.sparqlconnector.dto.FusekiQueryDto;
import ru.jena.fuseki.sparqlconnector.model.FusekiQueryResult;
import ru.jena.fuseki.sparqlconnector.service.impl.SparqlConnectorServiceImpl;

@RestController
@RequiredArgsConstructor
public class SparqlConnectorControllerImpl implements SparqlConnectorController {
    private final SparqlConnectorServiceImpl service;
    private final FusekiQueryConverter converter;

    @SneakyThrows
    @Override
    public void upload(String dataset, MultipartFile file) {
        service.upload(dataset, file.getBytes());
    }

    @Override
    public ResponseEntity<Resource> download(String dataset) {
        byte[] bytes = service.getBytes(dataset);
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.rdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(bytes));
    }

    @Override
    public FusekiQueryResult query(String dataset, FusekiQueryDto query) {
        return service.query(dataset, converter.convert(query));
    }

    @Override
    public ResponseEntity<Resource> queryAsCsv(String dataset, FusekiQueryDto query) {
        byte[] bytes = service.queryAsCsv(dataset, converter.convert(query));
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.csv");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(bytes));
    }
}
