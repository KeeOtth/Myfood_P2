package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaPedido implements Persistencia<Pedido> {

    private List<Pedido> carrinho = new ArrayList<>();
    private SerializacaoXML controle = new SerializacaoXML();
    private final String arquivo = "pedidos.xml";

    @Override
    public void iniciar() {
        carrinho = controle.DesserializarXML(carrinho, arquivo);
    }

    @Override
    public void salvar(Pedido modelo) {
        carrinho.add(modelo);
        controle.SerializarXML(carrinho, arquivo);
    }

    @Override
    public void remover(int id) {
        carrinho.removeIf(pedido -> pedido.getNumero() == id);
        controle.SerializarXML(carrinho, arquivo);
    }

    @Override
    public void limpar() {
        if (carrinho != null) {
            carrinho.clear();
        }
        controle.ApagarDadosXML(arquivo);
    }

    @Override
    public void editar(Pedido novo_modelo) {

    }

    @Override
    public Pedido buscar(int id) {
        for (Pedido pedido : carrinho) {
            if (pedido.getNumero() == id) {
                return pedido;
            }
        }
        return null;
    }

    @Override
    public List<Pedido> listar() {
        return carrinho;
    }
}
