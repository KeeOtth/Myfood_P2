package br.ufal.ic.p2.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Dono extends Usuario {
    private String cpf;
    private List<Empresa> comp_list;

    // O construtor vazio é necessário para a serialização e desserialização
    public Dono() {
    }

    public Dono(String nome, String email, String senha, String endereco, String cpf) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
        this.comp_list = new ArrayList<Empresa>();
    }

    // Getters e Setters

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna o valor do atributo desejado da classe
     * @param atributo o nome do atributo desejado
     * @return Uma 'string' com o valor do atributo desejado
     */
    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("cpf")) {
            return cpf;
        }
        return super.getAtributo(atributo);
    }

    public List<Empresa> getComp_list() {
        return comp_list;
    }

    public void setComp_list(List<Empresa> comp_list) {
        this.comp_list = comp_list;
    }

    public void addComp_list(Empresa empresa) {
        this.comp_list.add(empresa);
    }

}
