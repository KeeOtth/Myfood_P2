package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.exceptions.UnregisteredException;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contador = 1; //Para gerar o id �nico dos Pedidos
    private int id;
    private Usuario cliente;
    private Empresa empresa;
    private String estado;
    private List<Produto> prod_list = new ArrayList<>();
    private float valor_total;

    // O construtor vazio � necess�rio para a serializa��o e desserializa��o
    public Pedido () {
    }

    public Pedido (Usuario cliente, Empresa empresa) {
        this.id = contador++;
        this.cliente = cliente;
        this.empresa = empresa;
        this.estado = "aberto";
    }


    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Muda o estado do pedido
     * @throws UnregisteredException Retorna erro caso o pedido n�o exista ou n�o esteja aberto
     */
    public void changeEstado() throws UnregisteredException {
        switch (this.estado) {
            case "aberto" -> this.estado = "preparando";
            case "preparando" -> this.estado = "pronto";
            case "pronto" -> this.estado = "entregando";
            case "entregando" -> this.estado = "entregue";
            default -> throw new UnregisteredException("Este pedido nao esta aberto");
        }
    }

    public List<Produto> getProd_list() {
        return prod_list;
    }

    public void setProd_list(List<Produto> prod_list) {
        this.prod_list = prod_list;
    }

    public float getValor_total() {
        return valor_total;
    }

    public void setValor_total(float valor_total) {
        this.valor_total = valor_total;
    }

    /**
     * Adiciona um produto no prod_list e soma o valor no total da compra
     * @param produto O produto que deseja adicionar
     */
    public void addProductToList(Produto produto) {
        prod_list.add(produto);
        this.valor_total += produto.getValor();
    }

    /**
     * Remove um produto do prod_list e subtrai o valor no total da compra
     * @param produto O produto que deseja remover
     */
    public void removeProductFromList(Produto produto) {
        if (prod_list.remove(produto)){
            this.valor_total -= produto.getValor();
        }
    }

    public String toString(){
        return "Pedido{" +
                "produtos= '" + prod_list + '\'' +
                ", id= " + id +
                ", cliente= '" + cliente.getNome() + '\'' +
                ", empresa= " + empresa.getNome() +
                ", estado= " + estado +
                '}';
    }
}
