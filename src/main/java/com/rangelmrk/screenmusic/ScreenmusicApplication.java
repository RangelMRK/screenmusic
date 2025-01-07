package com.rangelmrk.screenmusic;

import com.rangelmrk.screenmusic.main.Main;
import com.rangelmrk.screenmusic.model.Musica;
import com.rangelmrk.screenmusic.repository.ArtistaRepository;
import com.rangelmrk.screenmusic.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmusicApplication implements CommandLineRunner {
	@Autowired
	private ArtistaRepository repositorioArtista;
	@Autowired
	private MusicaRepository repositorioMusica;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmusicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repositorioArtista, repositorioMusica);
		main.exibeMenu();
	}
}
