package br.tabajara.organization.recomendation.application;

import br.tabajara.organization.recomendation.controller.RecomendacaoController;
import br.tabajara.organization.recomendation.service.FileLoader;
import br.tabajara.organization.recomendation.view.RecomendacaoView;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            FileLoader.limparArquivos();

            RecomendacaoController controller = new RecomendacaoController();
            RecomendacaoView view = new RecomendacaoView();

            // Adicionar cursos
            System.out.println("\nAdicionando cursos iniciais...");
            controller.adicionarCurso("JavaScript Fundamentos", "iniciante");
            controller.adicionarCurso("React.js Essencial", "intermediario");
            controller.adicionarCurso("Arquitetura de Microsserviços", "avancado");
            controller.adicionarCurso("Python para Data Science", "intermediario");
            controller.adicionarCurso("Java Spring Boot", "intermediario");
            controller.adicionarCurso("AWS Cloud Practitioner", "iniciante");
            controller.adicionarCurso("DevOps & CI/CD", "avancado");
            controller.adicionarCurso("SQL para Análise de Dados", "iniciante");
            controller.adicionarCurso("Machine Learning com Python", "avancado");
            controller.adicionarCurso("Docker Fundamentals", "intermediario");

            // Adicionar usuários com nomes e cargos
            System.out.println("\nAdicionando usuários iniciais...");
            controller.adicionarUsuario(
                    1L,
                    "Marina Silva",
                    "Desenvolvedora Frontend Júnior",
                    Arrays.asList("JavaScript Fundamentos", "React.js Essencial"),
                    "iniciante"
            );

            controller.adicionarUsuario(
                    2L,
                    "Pedro Santos",
                    "Desenvolvedor Backend Pleno",
                    Arrays.asList("Java Spring Boot", "Docker Fundamentals"),
                    "intermediario"
            );

            controller.adicionarUsuario(
                    3L,
                    "Ana Costa",
                    "Iniciante em Data Science",
                    Arrays.asList("Python para Data Science", "SQL para Análise de Dados"),
                    "iniciante"
            );

            controller.adicionarUsuario(
                    4L,
                    "Carlos Oliveira",
                    "Especialista DevOps",
                    Arrays.asList("DevOps & CI/CD", "Docker Fundamentals"),
                    "avancado"
            );

            controller.adicionarUsuario(
                    5L,
                    "Juliana Pereira",
                    "Cientista de Dados",
                    Arrays.asList("Machine Learning com Python", "Python para Data Science"),
                    "intermediario"
            );

            // Mostrar catálogo
            System.out.println("\n=== Catálogo de Cursos ===");
            view.mostrarCursos();

            // Mostrar recomendações
            System.out.println("\n=== Recomendações Personalizadas ===");
            List<Long> userIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);

            for (Long userId : userIds) {
                System.out.println("\nGerando recomendações para usuário #" + userId);
                view.mostrarTrilha(userId, 3);
            }

        } catch (Exception e) {
            System.err.println("Erro ao executar o sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}