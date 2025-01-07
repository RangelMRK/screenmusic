package com.rangelmrk.screenmusic.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double duracao;

    private Integer ouvintes;

    private Integer reproducoes;

    private List<String> generos;

    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    public Musica() {}

    public Musica(DadosMusica dadosMusica){
        this.nome = dadosMusica.nome();
        this.duracao = dadosMusica.duracao();
        this.ouvintes = dadosMusica.ouvintes();
        this.reproducoes = dadosMusica.reproducoes();
        this.generos = dadosMusica.generos();
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

    public Double getDuracao() {
        return duracao;
    }

    public void setDuracao(Double duracao) {
        this.duracao = duracao;
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

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return  "Titulo = '" + nome + '\'' +
                "| Duração = " + duracao +
                "| Ouvintes = " + ouvintes +
                "| Reproduções = " + reproducoes +
                "| Gêneros: " + String.join(", ", generos);
    }
}


