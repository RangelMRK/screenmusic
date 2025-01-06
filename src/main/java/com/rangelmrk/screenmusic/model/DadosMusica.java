package com.rangelmrk.screenmusic.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DadosMusica(@JsonAlias("name") String nome,
                          @JsonAlias("duration") Double duracao,
                          @JsonAlias("listeners") Integer ouvintes,
                          @JsonAlias("playcount") Integer reproducoes,
                          @JsonProperty("summary") String resumo) {
}
