package br.ufal.ic.p2.myfood.models;

import java.io.Serializable;

public class Usuario implements Serializable{
    private static int contador = 0; //Pra gerar o id único, estático para ser compartilhado entre tosdas as instâncias.
    private int id;
    private String nome;
    private String email;
    private String senha;


    public Usuario(String nome, String email, String senha){
        this.id = gerarIdUnico();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    private synchronized static int gerarIdUnico() {
        return ++contador;
    }

    public int getId() {
        return id;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //    public Map<String, String> getAtributos() {
//        return atributos;
//    }

//    public void setAtributos(Map<String, String> atributos) {
//        this.atributos = atributos;
//    }
}
