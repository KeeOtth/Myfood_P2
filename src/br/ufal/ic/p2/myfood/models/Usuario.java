package br.ufal.ic.p2.myfood.models;

import java.io.Serializable;

public class Usuario implements Serializable{
    private static int contador = 0; //Pra gerar o id único, estático para ser compartilhado entre todas as instâncias.
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;


    public Usuario(String nome, String email, String senha, String endereco){
        this.id = gerarIdUnico();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
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
            case "email" -> email;
            case "senha" -> senha;
            case "edereco" -> endereco;
            default -> null;
        };
    }

    @Override
    public String toString(){
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + " senha=" + senha + "]";
    }
}
