package com.rangelmrk.screenmusic.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoArtista tipoArtista;
    private Integer ouvintes;
    private Integer reproducoes;
    private List<String> generos;
    private String resumo;


    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Musica> musicas = new ArrayList<>();

    public Artista(){}

    public Artista(DadosArtista dadosArtista, TipoArtista tipoArtista){
        this.nome = dadosArtista.nome();
        this.tipoArtista = tipoArtista;
        this.ouvintes = dadosArtista.ouvintes();
        this.reproducoes = dadosArtista.reproducoes();
        this.generos = dadosArtista.generos();
        this.resumo = dadosArtista.resumo();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoArtista getTipoArtista() {
        return tipoArtista;
    }

    public void setTipoArtista(TipoArtista tipoArtista) {
        this.tipoArtista = tipoArtista;
    }

    public Integer getOuvintes() {
        return ouvintes;
    }

    public void setOuvintes(Integer ouvintes) {
        this.ouvintes = ouvintes;
    }

    public Integer getReproducoes() {
        return reproducoes;
    }

    public void setReproducoes(Integer reproducoes) {
        this.reproducoes = reproducoes;
    }

    public List getGeneros() {
        return generos;
    }

    public void setGeneros(List generos) {
        this.generos = generos;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        musicas.forEach(m -> m.setArtista(this));
        this.musicas = musicas;
    }

    @Override
    public String toString() {
        return "Artista: " + nome +
                "| Tipo: " + tipoArtista +
                "| Ouvintes: " + ouvintes +
                "| Reproduções: " + reproducoes +
                "| Gêneros: " + String.join(", ", generos) +
                "| Resumo da Bio = '" + resumo + '\'' +
                "| Músicas = " + musicas;
    }
}
