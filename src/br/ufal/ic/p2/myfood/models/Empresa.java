package br.ufal.ic.p2.myfood.models;

public class Empresa {
    private static int contador = 1;
    private int id;
    private String nome;
    private String endereco;

    public Empresa() {}

    public Empresa(String nome, String endereco) {
        this.id = contador++;
        this.nome = nome;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getAtributo(String atributo) {
        return switch(atributo) {
            case "id" -> String.valueOf(id);
            case "nome" -> nome;
            case "endereco" -> endereco;
            default -> null;
        };
    }
}
