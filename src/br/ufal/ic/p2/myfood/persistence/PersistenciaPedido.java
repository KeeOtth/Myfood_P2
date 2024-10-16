package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaPedido implements Persistencia<Pedido> {

    private List<Pedido> carrinho = new ArrayList<>();
    private SerializacaoXML controle = new SerializacaoXML();
    private final String arquivo = "xml/pedidos.xml";

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
        carrinho.removeIf(pedido -> pedido.getId() == id);
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
        controle.SerializarXML(carrinho, arquivo);
    }

    @Override
    public Pedido buscar(int id) {
        for (Pedido pedido : carrinho) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    @Override
    public List<Pedido> listar() {
        return carrinho;
    }

    @Override
    public void atualizar() {
        controle.SerializarXML(carrinho, arquivo);
    }
}
