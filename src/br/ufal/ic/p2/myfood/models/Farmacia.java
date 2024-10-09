package br.ufal.ic.p2.myfood.models;

public class Farmacia extends Empresa {
    boolean aberto24;
    int nFuncionario;

    // O construtor vazio é necessário para a serialização e desserialização
    public Farmacia() {
    }

    public Farmacia(String nome, String endereco, Dono dono, boolean aberto24, int nFuncionario) {
        super(nome, endereco, dono);
        this.aberto24 = aberto24;
        this.nFuncionario = nFuncionario;
    }

    // Setters e Getters
    public boolean isAberto24() {
        return aberto24;
    }

    public void setAberto24(boolean aberto24) {
        this.aberto24 = aberto24;
    }

    public int getNFuncionario() {
        return nFuncionario;
    }

    public void setNFuncionario(int nFuncionario) {
        this.nFuncionario = nFuncionario;
    }

    /**
     * Retorna o valor do atributo desejado da classe
     * @param atributo o nome do atributo desejado
     * @return Uma 'string' com o valor do atributo desejado
     */
    @Override
    public String getAtributo(String atributo) {
        return switch (atributo) {
            case "aberto24Horas" -> String.valueOf(aberto24);
            case "numeroFuncionarios" -> String.valueOf(nFuncionario);
            default -> super.getAtributo(atributo);
        };
    }


}
