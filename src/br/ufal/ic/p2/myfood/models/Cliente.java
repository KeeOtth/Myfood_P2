package br.ufal.ic.p2.myfood.models;

public class Cliente extends Usuario {

    private String endereco;

    public Cliente(String nome, String email, String senha, String endereco) {
        super(nome, email, senha, endereco);
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String getAtributo(String atributo) {
        return super.getAtributo(atributo); //Faz sentido existir isso aqui? não sei.
    }
}
