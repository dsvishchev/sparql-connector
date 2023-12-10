package ru.jena.fuseki.sparqlconnector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SelectFusekiQuery implements FusekiQuery {
    @NotBlank
    private String subject;
    @NotBlank
    private String predicate;
    @NotBlank
    private String object;
    @Size(max = 20)
    @NotEmpty
    @Builder.Default
    private Set<String> variables = Set.of();
}
