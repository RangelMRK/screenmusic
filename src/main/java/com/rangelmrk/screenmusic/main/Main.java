package com.rangelmrk.screenmusic.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangelmrk.screenmusic.model.ArtistaDTO;
import com.rangelmrk.screenmusic.model.DadosArtista;
import com.rangelmrk.screenmusic.service.ConsumoApi;
import com.rangelmrk.screenmusic.service.ConverteDados;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private Scanner input = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private ObjectMapper objectMapper = new ObjectMapper();
    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
    private final String API_KEY = "&api_key=" + System.getenv("LAST_FM_KEY") + "&format=json";
    private final String ENDERECOMUSICA = "http://ws.audioscrobbler.com/2.0/?method=track.getinfo&track=";
    private final String ENDERECOALBUM = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&album=";
    private final String ENDERECOARTISTA = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=";


    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 9) {
            var menu = """
                    *** Screen Sound Músicas ***                    
                    
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");

            }
        }
    }

    private DadosArtista converterParaDadosArtista(String json){
        try {
            ArtistaDTO dto = objectMapper.readValue(json, ArtistaDTO.class);
            return new DadosArtista(
                    dto.artista.name,
                    Integer.parseInt(dto.artista.stats.listeners),
                    Integer.parseInt(dto.artista.stats.playcount),
                    dto.artista.bio.summary
            );
        } catch (JsonProcessingException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private DadosArtista getDadosArtista()  {
        System.out.println("Digite o nome do artista a ser buscado:");
        var nomeArtista = input.nextLine();
        var json = consumoApi.obterDados(ENDERECOARTISTA + nomeArtista.replace(" ", "+") + API_KEY);
        return converterParaDadosArtista(json);
    }

    private void cadastrarArtistas() {
        DadosArtista artista = getDadosArtista();
        if (artista != null) {
            System.out.println("Nome: " + artista.nome());
            System.out.println("Ouvintes: " + numberFormat.format(artista.ouvintes()));
            System.out.println("Reproduções: " + numberFormat.format(artista.reproducoes()));
            System.out.println("Resumo da Bio: " + artista.resumo());
        } else {
            System.out.println("Não foi possível obter os dados do artista.");
        }



    }

    private void cadastrarMusicas() {

    }

    private void listarMusicas() {

    }

    private void buscarMusicasPorArtista() {

    }

    private void pesquisarDadosDoArtista() {

    }


}