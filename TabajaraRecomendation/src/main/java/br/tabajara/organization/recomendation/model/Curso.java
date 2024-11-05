package br.tabajara.organization.recomendation.model;

public class Curso {
    private String nome;
    private String nivel;

    public Curso(String nome, String nivel) {
        this.nome = nome;
        this.nivel = nivel;
    }

    public String getNome() { return nome; }
    public String getNivel() { return nivel; }

    @Override
    public String toString() {
        return "Curso{nome='" + nome + "', nivel='" + nivel + "'}";
    }
}
