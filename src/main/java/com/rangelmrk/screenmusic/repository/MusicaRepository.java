package com.rangelmrk.screenmusic.repository;

import com.rangelmrk.screenmusic.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
    @Query("SELECT m FROM Musica m WHERE LOWER(m.artista.nome) LIKE LOWER(CONCAT('%', :nomeArtista, '%'))")
    List<Musica> retornaMusica(String nomeArtista);
}
