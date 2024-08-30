package br.ufal.ic.p2.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private static int contador = 1;
    private int id;
    private String nome;
    private String endereco;
    private Dono dono;
    private List<Produto> prod_list;

    public Empresa() {
    }

    public Empresa(String nome, String endereco, Dono dono) {
        this.id = contador++;
        this.nome = nome;
        this.endereco = endereco;
        this.dono = dono;
        prod_list = new ArrayList<>();
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

    public Dono getDono() {
        return dono;
    }

    public void setDono(Dono dono) {
        this.dono = dono;
    }

    public String getAtributo(String atributo) {
        return switch (atributo) {
            case "id" -> String.valueOf(id);
            case "nome" -> nome;
            case "endereco" -> endereco;
            default -> null;
        };
    }

    public List<Produto> getProd_list() {
        return prod_list;
    }

    public void setProd_list(List<Produto> prod_list) {
        this.prod_list = prod_list;
    }

    public void addProd_list(Produto produto){
        this.prod_list.add(produto);
    }

    @Override
    public String toString() {
        return "[" + nome + ", " + endereco + "]";
    }
}
