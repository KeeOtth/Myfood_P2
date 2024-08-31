package br.ufal.ic.p2.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contador = 1;
    private int numero;
    private String cliente;
    private String empresa;
    private String estado;
    private final List<Produto> prod_list = new ArrayList<>();
    private float valor_total;

    public Pedido () {

    }

    public Pedido (Usuario cliente, Empresa empresa) {
        this.numero = contador++;
        this.cliente = cliente.getNome();
        this.empresa = empresa.getNome();
        this.estado = "aberto";
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado() throws Exception {
        if (this.estado.equals("aberto")){
            this.estado = "fechado";
        }
        else {
            throw new Exception("Este pedido nao esta aberto");
        }
    }

    public List<Produto> getProd_list() {
        return prod_list;
    }

    public float getValor_total() {
        return valor_total;
    }

    public void setValor_total(float valor_total) {
        this.valor_total = valor_total;
    }

    public void addProductToList(Produto produto) {
        prod_list.add(produto);
        this.valor_total += produto.getValor();
    }

    public void removeProductFromList(Produto produto) {
        prod_list.remove(produto);
        this.valor_total -= produto.getValor();
    }
}
