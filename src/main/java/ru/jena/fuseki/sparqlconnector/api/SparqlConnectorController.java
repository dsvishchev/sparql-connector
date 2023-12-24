package ru.jena.fuseki.sparqlconnector.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.jena.fuseki.sparqlconnector.dto.FusekiQueryDto;
import ru.jena.fuseki.sparqlconnector.model.FusekiQueryResult;

import static ru.jena.fuseki.sparqlconnector.constants.EndpointConstants.BASE;

@Tag(
        name = "SPARQL",
        description = "Apache Jena Fuseki connector api"
)
public interface SparqlConnectorController {

    @Operation(summary = "Загрузить RDF-файл")
    @ApiResponse(responseCode = "200")
    @PutMapping(value = BASE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void upload(@RequestParam(name = "dataset") String dataset, @RequestPart MultipartFile file);

    @Operation(summary = "Выгрузить RDF-файл")
    @ApiResponse(responseCode = "200")
    @GetMapping(value = BASE + "/read")
    ResponseEntity<Resource> download(@RequestParam(name = "dataset") String dataset);

    @Operation(summary = "Выполнить запрос к серверу")
    @ApiResponse(responseCode = "200")
    @PostMapping(value = BASE + "/query")
    FusekiQueryResult query(@RequestParam(name = "dataset") String dataset, @RequestBody FusekiQueryDto query);

    @Operation(summary = "Выполнить запрос к серверу и вернуть результат в формате CSV")
    @ApiResponse(responseCode = "200")
    @PostMapping(value = BASE + "/query/csv")
    ResponseEntity<Resource> queryAsCsv(@RequestParam(name = "dataset") String dataset, @RequestBody FusekiQueryDto query);
}
