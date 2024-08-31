package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaProduto implements Persistencia<Produto> {

    private List<Produto> prod_list = new ArrayList<>();
    private SerializacaoXML controle = new SerializacaoXML();
    private final String arquivo = "produtos.xml";

    @Override
    public void iniciar() {
        prod_list = controle.DesserializarXML(prod_list, arquivo);
    }

    @Override
    public void salvar(Produto modelo) {
        prod_list.add(modelo);
        controle.SerializarXML(prod_list, arquivo);
    }

    @Override
    public void remover(int id) {
        prod_list.removeIf(produto -> produto.getId() == id);
        controle.SerializarXML(prod_list, arquivo);
    }

    @Override
    public void limpar() {
        if (prod_list != null) {
            prod_list.clear();
        }
        controle.ApagarDadosXML(arquivo);
    }

    @Override
    public void editar(Produto novo_produto) {
        for (int i = 0; i < prod_list.size(); i++) {
            Produto produtoExistente = prod_list.get(i);
            if (produtoExistente.getId() == novo_produto.getId()) {
                prod_list.set(i, novo_produto);
                controle.SerializarXML(prod_list, arquivo);
                return;
            }
        }
    }

    @Override
    public Produto buscar(int id) {
        for (Produto prod : prod_list) {
            if (prod.getId() == id) {
                return prod;
            }
        }
        return null;
    }

    @Override
    public List<Produto> listar() {
        return prod_list;
    }

    @Override
    public void atualizar() {
        controle.SerializarXML(prod_list, arquivo);
    }
}
