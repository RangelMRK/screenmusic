# 🚧 Em desenvolvimento

# ScreenMusic 

Uma aplicação para armazenar dados de nossos artistas e músicas preferidos em um banco de dados relacional, permitindo a busca de informações por artistas e a consulta de dados sobre eles através da integração com a API do Last.FM.

##  Descrição

Este projeto foi desenvolvido com o objetivo de criar uma aplicação robusta para o gerenciamento de informações sobre artistas e suas músicas. Ele utiliza um banco de dados relacional (PostgreSQL) para armazenar os dados localmente e integra-se com a API do Last.FM para enriquecer as informações dos artistas com dados externos.

## ✨ Funcionalidades

*   **Cadastro de Artistas:** Permite adicionar informações sobre novos artistas ao banco de dados.
*   **Cadastro de Músicas:** Permite adicionar músicas associadas aos artistas cadastrados.
*   **Listagem de Músicas:** Exibe uma lista de todas as músicas cadastradas.
*   **Busca por Artistas:** Permite buscar músicas por nome do artista.
*   **Consulta de Dados do Artista:** Consulta informações adicionais sobre um artista através da API do Last.FM.
*   **Interface de Menu:** Interface de console simples para interação com o usuário.

## ️ Tecnologias Utilizadas

*   **Java:** Linguagem de programação principal.
*   **SpringBoot:** Framework para desenvolvimento rápido de aplicações Java.
*   **PostgreSQL:** Banco de dados relacional utilizado para persistência dos dados.
*   **Maven:** Gerenciador de dependências.
*   **JPA (Java Persistence API):** Framework para mapeamento objeto-relacional (ORM).
*   **Jackson:** Biblioteca para manipulação de JSON (utilizada na integração com a API do Last.FM).

## ⚙️ Pré-requisitos

*   Java Development Kit (JDK) instalado (versão 17 ou superior, recomendado).
*   Maven instalado.
*   PostgreSQL instalado e configurado.
*   Uma conta e chave de API do Last.FM (necessária para a funcionalidade de consulta de dados do artista).

##  Configuração

1.  Clone o repositório:

    ```bash
    git clone (https://github.com/RangelMRK/screenmusic.git)
    ```

2.  Navegue até o diretório do projeto:

    ```bash
    cd screen-sound-musicas
    ```

3.  Configure as credenciais do banco de dados no arquivo `application.properties` (ou `application.yml`) dentro de `src/main/resources`. Exemplo:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
    spring.datasource.username=seu_usuario_postgres
    spring.datasource.password=sua_senha_postgres
    ```

4.  Configure a chave da API do Last.FM no mesmo arquivo `application.properties`:

    ```properties
    lastfm.api.key=SUA_CHAVE_API_LASTFM
    ```

5.  Execute o projeto com Maven:

    ```bash
    mvn spring-boot:run
    ```

## ⌨️ Como Usar

Após a execução, a aplicação exibirá um menu no console com as opções disponíveis. Siga as instruções no menu para interagir com o sistema.

## ⏭️ Próximos Passos

*   Implementar testes unitários e de integração.
*   Melhorar a interface do usuário (ex: interface gráfica ou web).
*   Adicionar tratamento de erros e validações mais robustas.
*   Implementar paginação para listagens de músicas e artistas.

##  Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests
