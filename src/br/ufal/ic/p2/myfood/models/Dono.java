package br.ufal.ic.p2.myfood.models;

public class Dono extends Usuario{
    private String endereco;
    private String cpf;

    public Dono(String nome, String email, String senha, String endereco, String cpf) {
        super(nome, email, senha);
        this.endereco = endereco;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

//    public void setCpf(String cpf) { Faz sentido ter um método pra setar o CPF?
//       this.cpf = cpf;
//    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
