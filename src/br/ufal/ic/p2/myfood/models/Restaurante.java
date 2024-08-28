package br.ufal.ic.p2.myfood.models;

public class Restaurante extends Empresa {
    private String tipoCozinha;

    public Restaurante() {
    }

    public Restaurante(String nome, String endereco, int id_dono, String tipoCozinha) {
        super(nome, endereco, id_dono);
        this.tipoCozinha = tipoCozinha;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("tipoCozinha")) {
            return tipoCozinha;
        }
        return super.getAtributo(atributo);
    }
}
