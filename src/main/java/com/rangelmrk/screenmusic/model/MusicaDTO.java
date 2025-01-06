package com.rangelmrk.screenmusic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MusicaDTO {
    @JsonProperty("track")
    public MusicaDetalhes musica;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MusicaDetalhes {
        @JsonProperty("name")
        public String name;

        @JsonProperty("listeners")
        public String listeners;

        @JsonProperty("playcount")
        public String playcount;

        @JsonProperty("wiki")
        public Wiki wiki;


        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Wiki {
            @JsonProperty("summary")
            public String summary;
        }
    }
}

