package br.ufal.ic.p2.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Entregador extends Usuario{
    private String veiculo;
    private String placa;
    private String status;
    private List<Empresa> comp_list;

    // O construtor vazio é necessário para a serialização e desserialização
    public Entregador() {
    }

    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
        this.status = "disponivel";
        comp_list = new ArrayList<Empresa>();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void changeEstado() {
        switch (this.status){
            case "disponivel" -> this.status = "entregando";
            case "entregando" -> this.status = "disponivel";
        }
    }

    public List<Empresa> getComp_list() {
        return comp_list;
    }

    public void setComp_list(List<Empresa> comp_list) {
        this.comp_list = comp_list;
    }

    public void addComp(Empresa comp) {
        comp_list.add(comp);
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
