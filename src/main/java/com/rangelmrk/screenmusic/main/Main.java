package com.rangelmrk.screenmusic.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangelmrk.screenmusic.model.*;
import com.rangelmrk.screenmusic.service.ConsumoApi;
import com.rangelmrk.screenmusic.service.ConverteDados;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private Scanner input = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private ObjectMapper objectMapper = new ObjectMapper();
    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
    private final String API_KEY = "&api_key=" + System.getenv("LAST_FM_KEY");
    private String buscaArtista;
    private String buscaMusica;
    private String criarEnderecoArtista() {
        return "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist="
                + buscaArtista + API_KEY + "&format=json";
    }
    private String criarEnderecoMusica() {
        return "http://ws.audioscrobbler.com/2.0/?method=track.getInfo"
                + API_KEY + "&artist=" + buscaArtista + "&track="
                + buscaMusica + "&format=json";
    }



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
            List<String> generos = dto.artista.tags.tagList.stream()
                    .map(tag -> tag.genero)
                    .toList();
            return new DadosArtista(
                    dto.artista.name,
                    Integer.parseInt(dto.artista.stats.listeners),
                    Integer.parseInt(dto.artista.stats.playcount),
                    generos,
                    dto.artista.bio.summary
            );
        } catch (JsonProcessingException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }


    private DadosArtista getDadosArtista()  {
        System.out.println("Digite o nome do artista a ser buscado:");
        buscaArtista = input.nextLine().toLowerCase().replace(" ", "+");
        String endereco = criarEnderecoArtista();
        var json = consumoApi.obterDados(endereco);
        return converterParaDadosArtista(json);
    }

    private DadosMusica converterParaDadosMusica(String json){
        try {
            MusicaDTO dto = objectMapper.readValue(json, MusicaDTO.class);
            List<String> generos = dto.musica.tags.tagList.stream()
                    .map(tag -> tag.generos)
                    .toList();
            return new DadosMusica(
                    dto.musica.name,
                    (Double.parseDouble(dto.musica.duration)/60000),
                    Integer.parseInt(dto.musica.listeners),
                    Integer.parseInt(dto.musica.playcount),
                    generos,
                    dto.musica.wiki.summary
            );
        } catch (JsonProcessingException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private DadosMusica getDadosMusica(){
        System.out.println("Digite o nome do artista a ser buscado:");
        buscaArtista = input.nextLine().toLowerCase().replace(" ", "+");
        System.out.println("Qual música deseja buscar?");
        buscaMusica = input.nextLine().toLowerCase().replace(" ", "+");
        String endereco = criarEnderecoMusica();
        var json = consumoApi.obterDados(endereco);
        return converterParaDadosMusica(json);
    }

    //    private void cadastrarArtistas() {
//        DadosArtista artista = getDadosArtista();
//        if (artista != null) {
//            System.out.println("Nome: " + artista.nome());
//            System.out.println("Ouvintes: " + numberFormat.format(artista.ouvintes()));
//            System.out.println("Reproduções: " + numberFormat.format(artista.reproducoes()));
//            System.out.println("Gêneros: " + String.join(", ", artista.generos()));
//            System.out.println("Resumo da Bio: " + artista.resumo());
//
//        } else {
//            System.out.println("Não foi possível obter os dados do artista.");
//        }
//    }
    private void cadastrarArtistas() {
        DadosArtista artista = getDadosArtista();

        if (artista != null) {
            System.out.println("Informe o tipo de artista:");
            System.out.println("1 - Solo");
            System.out.println("2 - Dupla");
            System.out.println("3 - Banda");
            int tipoEscolhido = input.nextInt();
            input.nextLine();

            TipoArtista tipoArtista;
            switch (tipoEscolhido) {
                case 1 -> tipoArtista = TipoArtista.SOLO;
                case 2 -> tipoArtista = TipoArtista.DUPLA;
                case 3 -> tipoArtista = TipoArtista.BANDA;
                default -> {
                    System.out.println("Tipo inválido. Cadastrando como SOLO por padrão.");
                    tipoArtista = TipoArtista.SOLO;
                }
            }

            Artista novoArtista = new Artista(artista, tipoArtista);
            System.out.println("Artista cadastrado com sucesso!");
            System.out.println(novoArtista);
        } else {
            System.out.println("Não foi possível obter os dados do artista.");
        }
    }

    private void cadastrarMusicas() {
        DadosMusica musica = getDadosMusica();
        if (buscaArtista != null) {
            System.out.println("Nome: " + musica.nome());
            System.out.printf("Duração: %.2f Minutos", musica.duracao());
            System.out.println("\nOuvintes: " + numberFormat.format(musica.ouvintes()));
            System.out.println("Reproduções: " + numberFormat.format(musica.reproducoes()));
            System.out.println("Gêneros: " + String.join(", ", musica.generos()));
            System.out.println("Resumo da Wiki: " + musica.resumo());
        } else {
            System.out.println("Não foi possível obter os dados da Música.");
        }
    }

    private void listarMusicas() {

    }

    private void buscarMusicasPorArtista() {

    }

    private void pesquisarDadosDoArtista() {

    }


}