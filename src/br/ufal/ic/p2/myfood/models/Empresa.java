package br.ufal.ic.p2.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private static int contador = 1; //Para gerar o id �nico das Empresas
    private int id;
    private String nome;
    private String endereco;
    private Dono dono;
    private List<Produto> prod_list;
    private List<Entregador> entregador_list;

    // O construtor vazio � necess�rio para a serializa��o e desserializa��o
    public Empresa() {
    }

    public Empresa(String nome, String endereco, Dono dono) {
        this.id = contador++;
        this.nome = nome;
        this.endereco = endereco;
        this.dono = dono;
        prod_list = new ArrayList<>();
        entregador_list = new ArrayList<>();
    }

    // Getters e Setters

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
            case "dono" -> dono.getNome();
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

    public List<Entregador> getEntregadores() {
        return entregador_list;
    }

    public void setEntregador_list(List<Entregador> entregador_list) {
        this.entregador_list = entregador_list;
    }

    public void addEntregador_list(Entregador entregador){
        this.entregador_list.add(entregador);
    }

    @Override
    public String toString() {
        return "[" + nome + ", " + endereco + "]";
    }
}
