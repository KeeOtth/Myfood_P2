package br.ufal.ic.p2.myfood.models;

public class Mercado extends Empresa{
    private String tipoMercado;
    private String abre;
    private String fecha;

    // O construtor vazio é necessário para a serialização e desserialização
    public Mercado() {
    }

    public Mercado(String nome, String endereco, Dono dono, String tipoMercado, String abre, String fecha) {
        super(nome, endereco, dono);
        this.tipoMercado = tipoMercado;
        this.abre = abre;
        this.fecha = fecha;
    }

    // Setters e Getters
    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    public String getAbre() {
        return abre;
    }

    public void setAbre(String abre) {
        this.abre = abre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Retorna o valor do atributo desejado da classe
     * @param atributo o nome do atributo desejado
     * @return Uma 'string' com o valor do atributo desejado
     */
    @Override
    public String getAtributo(String atributo) {
        return switch (atributo) {
            case "tipoMercado" -> tipoMercado;
            case "abre" -> abre;
            case "fecha" -> fecha;
            default -> super.getAtributo(atributo);
        };
    }

}
