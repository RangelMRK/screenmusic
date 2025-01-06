package com.rangelmrk.screenmusic.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosArtista(@JsonAlias("name") String nome,
                           @JsonAlias("listeners") Integer ouvintes,
                           @JsonAlias("playcount") Integer reproducoes,
                           @JsonProperty("summary") String resumo) {
}
