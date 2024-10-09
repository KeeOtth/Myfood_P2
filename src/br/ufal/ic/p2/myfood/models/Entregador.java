package br.ufal.ic.p2.myfood.models;

public class Entregador extends Usuario{
    private String veiculo;
    private String placa;

    // O construtor vazio é necessário para a serialização e desserialização
    public Entregador() {
    }

    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
    }

    // Getters e Setters
    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Retorna o valor do atributo desejado da classe
     * @param atributo o nome do atributo desejado
     * @return Uma 'string' com o valor do atributo desejado
     */
    @Override
    public String getAtributo(String atributo) {
        return switch (atributo) {
            case "veiculo" -> veiculo;
            case "placa" -> placa;
            default -> super.getAtributo(atributo);
        };
    }

    @Override
    public String toString() {
        return super.getEmail();
    }
}
