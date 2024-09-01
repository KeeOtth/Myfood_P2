package br.ufal.ic.p2.myfood.models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static int contador = 1; //Para gerar o id único dos Usuários
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    // O construtor vazio é necessário para a serialização e desserialização
    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String endereco) {
        this.id = contador++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o valor do atributo desejado da classe
     * @param atributo o nome do atributo desejado
     * @return Uma 'string' com o valor do atributo desejado
     */
    public String getAtributo(String atributo) {
        return switch (atributo) {
            case "id" -> String.valueOf(id);
            case "nome" -> nome;
            case "email" -> email;
            case "senha" -> senha;
            case "endereco" -> endereco;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + " senha=" + senha + "  endereço=" + endereco + "]";
    }
}
