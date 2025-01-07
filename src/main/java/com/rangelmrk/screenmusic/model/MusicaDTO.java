package com.rangelmrk.screenmusic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicaDTO {
    @JsonProperty("track")
    public MusicaDetalhes musica;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MusicaDetalhes {
        @JsonProperty("name")
        public String name;

        @JsonProperty("duration")
        public String duration;

        @JsonProperty("listeners")
        public String listeners;

        @JsonProperty("playcount")
        public String playcount;

        @JsonProperty("wiki")
        public Wiki wiki;



        @JsonProperty("toptags")
        public Tags tags;


        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Tags {
            @JsonProperty("tag")
            public List<Tipo> tagList;

        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Tipo {
            @JsonProperty("name")
            public String generos;
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Wiki {
            @JsonProperty("summary")
            public String summary;
        }
    }
}

