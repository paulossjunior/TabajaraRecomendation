package br.tabajara.organization.recomendation.model;

import java.util.List;

public class Usuario {
    private long id;
    private String nome;
    private String cargo;
    private List<String> interessesCursos;
    private String nivelPreferido;


    public Usuario(long id, String nome, String cargo, List<String> interessesCursos, String nivelPreferido) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.interessesCursos = interessesCursos;
        this.nivelPreferido = nivelPreferido;
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getCargo() { return cargo; }
    public List<String> getInteressesCursos() { return interessesCursos; }
    public String getNivelPreferido() { return nivelPreferido; }

    @Override
    public String toString() {
        return String.format("%s (%s)", nome, cargo);
    }
}
