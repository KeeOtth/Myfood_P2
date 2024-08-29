package br.ufal.ic.p2.myfood.models;

public class Produto {
    private static int contador = 1;
    private int id;
    private String nome;
    private float valor;
    private String categoria;
    private int id_empresa;

    public Produto() {
    }

    public Produto(String nome, float valor, String categoria) {
        this.id = contador++;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }
}
