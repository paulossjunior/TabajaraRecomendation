package br.tabajara.organization.recomendation.controller;


import br.tabajara.organization.recomendation.model.Curso;
import br.tabajara.organization.recomendation.model.Usuario;
import br.tabajara.organization.recomendation.service.RecomendacaoService;

import java.util.List;
public class RecomendacaoController {
    private RecomendacaoService service;

    public RecomendacaoController() {
        this.service = new RecomendacaoService();
    }

    public void adicionarCurso(String nome, String nivel) {
        service.adicionarCurso(nome, nivel);
    }

    public void adicionarUsuario(long id, String nome, String cargo, List<String> interessesCursos, String nivelPreferido) {
        service.adicionarUsuario(id, nome, cargo, interessesCursos, nivelPreferido);
    }

    public List<String> gerarTrilhaAprendizagem(long userId, int numRecomendacoes) {
        return service.gerarTrilhaAprendizagem(userId, numRecomendacoes);
    }

    public List<Curso> listarCursos() {
        return service.getCursos();
    }

    public Usuario buscarUsuarioPorId(long userId) {
        return service.buscarUsuarioPorId(userId);
    }

}
