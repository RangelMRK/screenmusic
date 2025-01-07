package com.rangelmrk.screenmusic.repository;

import com.rangelmrk.screenmusic.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Artista a LEFT JOIN FETCH a.musicas WHERE a.nome ILIKE :nome")
    Optional<Artista> acharPeloNome(String nome);
}
