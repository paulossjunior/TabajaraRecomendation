package br.tabajara.organization.recomendation.service;

import br.tabajara.organization.recomendation.model.Curso;
import br.tabajara.organization.recomendation.model.Usuario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class RecomendacaoService {
    private List<Curso> cursos;
    private List<Usuario> usuarios;
    private Path dadosPreferencias;

    public RecomendacaoService() {
        try {
            // Carregar dados dos arquivos
            this.cursos = FileLoader.carregarCursos();
            this.usuarios = FileLoader.carregarUsuarios();

            // Verificar se os dados foram carregados
            if (this.cursos.isEmpty()) {
                inicializarCursos();
            }
            if (this.usuarios.isEmpty()) {
                inicializarUsuarios();
            }

            // Configurar arquivo de preferências
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "recomendacao");
            Files.createDirectories(tempDir);
            this.dadosPreferencias = tempDir.resolve("preferencias.csv");

        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar o serviço", e);
        }
    }
    private void inicializarCursos() {
        // Dados iniciais de cursos
        cursos.add(new Curso("JavaScript Fundamentos", "iniciante"));
        cursos.add(new Curso("React.js Essencial", "intermediario"));
        cursos.add(new Curso("Arquitetura de Microsserviços", "avancado"));
        cursos.add(new Curso("Python para Data Science", "intermediario"));
        cursos.add(new Curso("Java Spring Boot", "intermediario"));
        cursos.add(new Curso("AWS Cloud Practitioner", "iniciante"));
        cursos.add(new Curso("DevOps & CI/CD", "avancado"));
        cursos.add(new Curso("SQL para Análise de Dados", "iniciante"));
        cursos.add(new Curso("Machine Learning com Python", "avancado"));
        cursos.add(new Curso("Docker Fundamentals", "intermediario"));

        // Salvar cursos no arquivo
        FileLoader.salvarCursos(cursos);
    }

    private void inicializarUsuarios() {
        // Dados iniciais de usuários
        usuarios.add(new Usuario(
                1L,
                "Marina Silva",
                "Desenvolvedora Frontend Júnior",
                Arrays.asList("JavaScript Fundamentos", "React.js Essencial"),
                "iniciante"
        ));

        usuarios.add(new Usuario(
                2L,
                "Pedro Santos",
                "Desenvolvedor Backend Pleno",
                Arrays.asList("Java Spring Boot", "Docker Fundamentals"),
                "intermediario"
        ));

        usuarios.add(new Usuario(
                3L,
                "Ana Costa",
                "Iniciante em Data Science",
                Arrays.asList("Python para Data Science", "SQL para Análise de Dados"),
                "iniciante"
        ));

        usuarios.add(new Usuario(
                4L,
                "Carlos Oliveira",
                "Especialista DevOps",
                Arrays.asList("DevOps & CI/CD", "Docker Fundamentals"),
                "avancado"
        ));

        usuarios.add(new Usuario(
                5L,
                "Juliana Pereira",
                "Cientista de Dados",
                Arrays.asList("Machine Learning com Python", "Python para Data Science"),
                "intermediario"
        ));
        // Salvar usuários no arquivo
        FileLoader.salvarUsuarios(usuarios);
    }

    public void adicionarCurso(String nome, String nivel) {
        cursos.add(new Curso(nome, nivel));
    }

    public Usuario buscarUsuarioPorId(long userId) {
        return usuarios.stream()
                .filter(u -> u.getId() == userId)
                .findFirst()
                .orElse(null);
    }

    public void adicionarUsuario(long id, String nome, String cargo, List<String> interessesCursos, String nivelPreferido) {
        usuarios.add(new Usuario(id, nome, cargo, interessesCursos, nivelPreferido));
        FileLoader.salvarUsuarios(usuarios);
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Curso> getCursos() {
        return new ArrayList<>(cursos);
    }

    private void gerarArquivoPreferencias() throws IOException {
        // Garantir que temos dados para gerar preferências
        if (usuarios.isEmpty() || cursos.isEmpty()) {
            throw new IllegalStateException("Não há dados suficientes para gerar recomendações");
        }

        // Criar arquivo de preferências com dados iniciais
        try (PrintWriter writer = new PrintWriter(new FileWriter(dadosPreferencias.toFile()))) {
            // Para cada usuário, gerar pelo menos uma preferência para garantir dados mínimos
            for (Usuario usuario : usuarios) {
                boolean temPreferencia = false;

                // Preferências baseadas nos interesses declarados
                for (String interesseCurso : usuario.getInteressesCursos()) {
                    for (int i = 0; i < cursos.size(); i++) {
                        Curso curso = cursos.get(i);
                        if (curso.getNome().equals(interesseCurso)) {
                            double pontuacao = calcularPontuacao(usuario.getNivelPreferido(), curso.getNivel());
                            writer.println(usuario.getId() + "," + i + "," + pontuacao);
                            temPreferencia = true;
                        }
                    }
                }

                // Se o usuário não tem nenhuma preferência, criar uma preferência padrão
                if (!temPreferencia) {
                    // Encontrar um curso do mesmo nível do usuário
                    for (int i = 0; i < cursos.size(); i++) {
                        if (cursos.get(i).getNivel().equals(usuario.getNivelPreferido())) {
                            writer.println(usuario.getId() + "," + i + ",3.0");
                            break;
                        }
                    }
                }
            }
        }

        // Verificar se o arquivo foi gerado e tem conteúdo
        if (Files.size(dadosPreferencias) == 0) {
            throw new IllegalStateException("Arquivo de preferências está vazio após geração");
        }
    }

    private double calcularPontuacao(String nivelUsuario, String nivelCurso) {
        if (nivelUsuario.equals(nivelCurso)) {
            return 5.0;
        } else if ((nivelUsuario.equals("intermediario") && nivelCurso.equals("iniciante")) ||
                (nivelUsuario.equals("avancado") && nivelCurso.equals("intermediario"))) {
            return 3.0;
        }
        return 1.0;
    }

    public List<String> gerarTrilhaAprendizagem(long userId, int numRecomendacoes) {
        try {
            // Verificar se o usuário existe
            boolean usuarioExiste = usuarios.stream().anyMatch(u -> u.getId() == userId);
            if (!usuarioExiste) {
                throw new IllegalArgumentException("Usuário não encontrado: " + userId);
            }

            // Gerar arquivo de preferências
            gerarArquivoPreferencias();

            // Criar modelo de dados
            DataModel model = new FileDataModel(dadosPreferencias.toFile());

            // Verificar se há dados suficientes
            if (model.getNumUsers() == 0 || model.getNumItems() == 0) {
                throw new IllegalStateException("Não há dados suficientes para gerar recomendações");
            }

            // Configurar o recomendador
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Gerar recomendações
            List<RecommendedItem> recommendations = recommender.recommend(userId, numRecomendacoes);
            List<String> trilha = new ArrayList<>();

            for (RecommendedItem recommendation : recommendations) {
                int cursoIdx = (int) recommendation.getItemID();
                if (cursoIdx < cursos.size()) {
                    trilha.add(cursos.get(cursoIdx).getNome());
                }
            }

            // Se não conseguiu recomendações específicas, recomendar cursos do mesmo nível
            if (trilha.isEmpty()) {
                String nivelUsuario = usuarios.stream()
                        .filter(u -> u.getId() == userId)
                        .findFirst()
                        .get()
                        .getNivelPreferido();

                cursos.stream()
                        .filter(c -> c.getNivel().equals(nivelUsuario))
                        .limit(numRecomendacoes)
                        .forEach(c -> trilha.add(c.getNome()));
            }

            return trilha;

        } catch (Exception e) {
            System.err.println("Erro ao gerar recomendações: " + e.getMessage());
            // Retornar lista vazia em caso de erro
            return new ArrayList<>();
        }
    }
}