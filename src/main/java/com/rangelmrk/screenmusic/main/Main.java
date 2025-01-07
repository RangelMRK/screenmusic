package com.rangelmrk.screenmusic.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangelmrk.screenmusic.model.*;
import com.rangelmrk.screenmusic.repository.ArtistaRepository;
import com.rangelmrk.screenmusic.repository.MusicaRepository;
import com.rangelmrk.screenmusic.service.ConsumoApi;
import com.rangelmrk.screenmusic.service.ConverteDados;

import java.text.NumberFormat;
import java.util.*;

import static java.lang.Integer.parseInt;

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

    private ArtistaRepository repositorioArtista;
    private MusicaRepository repositorioMusica;

    private List<Musica> musicaList;

    public Main(ArtistaRepository repositorioArtista, MusicaRepository repositorioMusica) {
        this.repositorioArtista = repositorioArtista;
        this.repositorioMusica = repositorioMusica;
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
                    parseInt(dto.artista.stats.listeners),
                    parseInt(dto.artista.stats.playcount),
                    generos
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
                    parseInt(dto.musica.listeners),
                    parseInt(dto.musica.playcount),
                    generos
            );
        } catch (JsonProcessingException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private DadosMusica getDadosMusica(String artista, String musica) {
        buscaArtista = artista.toLowerCase().replace(" ", "+");
        buscaMusica = musica.toLowerCase().replace(" ", "+");
        String endereco = criarEnderecoMusica();

        try {

            var json = consumoApi.obterDados(endereco);
            return converterParaDadosMusica(json);
        } catch (Exception e) {
            System.out.println("Erro ao buscar dados da música: " + e.getMessage());
        }

        return null;
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";
        while (cadastrarNovo.equalsIgnoreCase("s")){
            DadosArtista artista = getDadosArtista();
            if (artista != null) {
                try {
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
                    repositorioArtista.save(novoArtista);
                    System.out.println("Artista cadastrado com sucesso!");

                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, insira um número válido.");
                    input.nextLine();
                    continue;
                }


            } else {
                System.out.println("Não foi possível obter os dados do artista.");
            }
            System.out.println("Cadastrar novo Artista? (S/N)");
            cadastrarNovo = input.nextLine();
        }
    }

    private void cadastrarMusicas() {
        var cadastrarNovo = "S";
        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Digite o nome do artista a ser buscado:");
            var nomeArtista = input.nextLine();


            Optional<Artista> artista = repositorioArtista.findByNomeContainingIgnoreCase(nomeArtista);
            if (artista.isPresent()) {
                System.out.println("Artista encontrado: " + artista.get().getNome());

                System.out.println("Qual música deseja buscar?");
                var nomeMusica = input.nextLine();


                DadosMusica musicaDados = getDadosMusica(artista.get().getNome(), nomeMusica);
                if (musicaDados != null) {

                    Musica novaMusica = new Musica(musicaDados);
                    novaMusica.setArtista(artista.get());


                    repositorioMusica.save(novaMusica);
                    System.out.println("Música cadastrada com sucesso!");
                } else {
                    System.out.println("Não foi possível obter os dados da música.");
                }
            } else {
                System.out.println("Artista não encontrado. Não foi possível cadastrar a música.");
            }


            System.out.println("Cadastrar nova Música? (S/N)");
            cadastrarNovo = input.nextLine();
        }
    }

    private void listarMusicas() {
        musicaList = repositorioMusica.findAll();
        musicaList.stream()
                .sorted(Comparator.comparing(Musica::getNome))
                .forEach(System.out::println);

    }

    private void buscarMusicasPorArtista() {
        System.out.println("Digite o nome do artista que deseja exibir as músicas:");
        var nomeArtista = input.nextLine();

        List<Musica> musicasEncontradas = repositorioMusica.retornaMusica(nomeArtista);

        if (!musicasEncontradas.isEmpty()) {
            System.out.println("Músicas encontradas de " + nomeArtista + " :");
            musicasEncontradas.stream()
                    .sorted(Comparator.comparing(Musica::getNome))
                    .forEach(musica -> System.out.println("- " + musica.getNome()));
        } else {
            System.out.println("Nenhuma música encontrada para o artista: " + nomeArtista);
        }
    }

    private void pesquisarDadosDoArtista() {
        System.out.println("Digite o nome do artista que deseja pesquisar os dados :");
        var nomeArtista = input.nextLine();
        Optional<Artista> artista = repositorioArtista.acharPeloNome(nomeArtista);

        if(artista.isEmpty()){
            System.out.println("Nenhum artista com o nome '" + nomeArtista + "' foi encontrado");
        } else {
            Artista a = artista.get();
            System.out.println("Artista encontrado: ");
            System.out.println("Nome: " + a.getNome());
            System.out.println("Tipo: " + a.getTipoArtista());
            System.out.println("Ouvintes: " + numberFormat.format(a.getOuvintes()));
            System.out.println("Reproduções: " + numberFormat.format(a.getReproducoes()));
            System.out.println("Gêneros: " + String.join(", ", a.getGeneros()));

            if (!a.getMusicas().isEmpty()) {
                System.out.println("Músicas:");
                for (Musica musica : a.getMusicas()) {
                    System.out.println(" - " + musica.getNome());  // Exibindo o nome de cada música
                }
            } else {
                System.out.println("O artista não possui músicas cadastradas.");
            }
        }
    }


}