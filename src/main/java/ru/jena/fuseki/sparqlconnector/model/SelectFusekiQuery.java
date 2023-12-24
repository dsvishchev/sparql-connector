package ru.jena.fuseki.sparqlconnector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

import static ru.jena.fuseki.sparqlconnector.constants.QueryTypeConstants.SELECT;

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

    @Override
    public String getType() {
        return SELECT;
    }
}
