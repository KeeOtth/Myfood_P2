package br.ufal.ic.p2.myfood.models;

public class Restaurante extends Empresa {
    private String tipoCozinha;

    // O construtor vazio é necessário para a serialização e desserialização
    public Restaurante() {
    }

    public Restaurante(String nome, String endereco, Dono dono, String tipoCozinha) {
        super(nome, endereco, dono);
        this.tipoCozinha = tipoCozinha;
    }

    // Getters e Setters

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    /**
     * Retorna o valor do atributo desejado da classe
     * @param atributo o nome do atributo desejado
     * @return Uma 'string' com o valor do atributo desejado
     */
    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("tipoCozinha")) {
            return tipoCozinha;
        }
        return super.getAtributo(atributo);
    }
}
