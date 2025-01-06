package com.rangelmrk.screenmusic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistaDTO {
    @JsonProperty("artist")
    public ArtistaDetalhes artista;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArtistaDetalhes {
        @JsonProperty("name")
        public String name;

        @JsonProperty("stats")
        public Stats stats;

        @JsonProperty("bio")
        public Bio bio;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Stats {
            @JsonProperty("listeners")
            public String listeners;

            @JsonProperty("playcount")
            public String playcount;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Bio {
            @JsonProperty("summary")
            public String summary;
        }
    }
}
