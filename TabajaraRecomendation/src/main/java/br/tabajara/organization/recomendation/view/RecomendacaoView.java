package br.tabajara.organization.recomendation.view;

import br.tabajara.organization.recomendation.controller.RecomendacaoController;
import br.tabajara.organization.recomendation.model.Curso;
import br.tabajara.organization.recomendation.model.Usuario;

import java.util.List;

public class RecomendacaoView {
    private RecomendacaoController controller;

    public RecomendacaoView() {
        this.controller = new RecomendacaoController();
    }

    public void mostrarTrilha(long userId, int numRecomendacoes) {
        try {
            List<String> trilha = controller.gerarTrilhaAprendizagem(userId, numRecomendacoes);
            Usuario usuario = controller.buscarUsuarioPorId(userId);

            if (usuario != null) {
                System.out.printf("\nRecomendações para %s (%s):\n",
                        usuario.getNome(), usuario.getCargo());

                if (trilha.isEmpty()) {
                    System.out.println("Nenhuma recomendação encontrada.");
                } else {
                    for (String curso : trilha) {
                        System.out.println("- " + curso);
                    }
                }
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao mostrar trilha: " + e.getMessage());
        }
    }

    public void mostrarCursos() {
        List<Curso> cursos = controller.listarCursos();
        for (Curso curso : cursos) {
            System.out.println("- " + curso.getNome() + " (" + curso.getNivel() + ")");
        }
    }
}

