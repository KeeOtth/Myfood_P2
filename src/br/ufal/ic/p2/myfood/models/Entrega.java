package br.ufal.ic.p2.myfood.models;

import java.util.List;

public class Entrega {
    private static int contador = 1; //Para gerar o id único dos Usuários
    private int id;
    private Usuario cliente;
    private Empresa empresa;
    private Pedido pedido;
    private String destino;
    private List<Produto> produtos;



}
