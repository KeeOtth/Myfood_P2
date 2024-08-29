package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaProduto implements Persistencia<Produto> {
    private List<Produto> prod_list = new ArrayList<>();
    private SerializacaoXML controle = new SerializacaoXML();
    private final String arquivo = "produtos.xml";

    @Override
    public void iniciar(String caminho) {

    }

    @Override
    public void salvar(Produto modelo) {

    }

    @Override
    public void remover(int id) {

    }

    @Override
    public void limpar() {

    }

    @Override
    public void editar(String id, Produto novo_modelo) {

    }

    @Override
    public Produto buscar(int id) {
        return null;
    }

    @Override
    public List<Produto> listar() {
        return List.of();
    }
}
