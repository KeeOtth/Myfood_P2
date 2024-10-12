package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Entrega;
import br.ufal.ic.p2.myfood.models.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaEntrega implements Persistencia<Entrega> {

    private List<Entrega> delivery_list = new ArrayList<>();
    private SerializacaoXML controle = new SerializacaoXML();
    private final String arquivo = "xml/entregas.xml";

    @Override
    public void iniciar() {
        delivery_list = controle.DesserializarXML(delivery_list, arquivo);
    }

    @Override
    public void salvar(Entrega modelo) {
        delivery_list.add(modelo);
        controle.SerializarXML(delivery_list, arquivo);
    }

    @Override
    public void remover(int id) {
        delivery_list.removeIf(comp -> comp.getId() == id);
        controle.SerializarXML(delivery_list, arquivo);
    }

    @Override
    public void limpar() {
        if (delivery_list != null) {
            delivery_list.clear();
        }

        controle.ApagarDadosXML(arquivo);
    }

    @Override
    public void editar(Entrega nova_entrega) {
        for (int i = 0; i < delivery_list.size(); i++) {
            Entrega entregaExistente = delivery_list.get(i);
            if (entregaExistente.getId() == nova_entrega.getId()) {
                delivery_list.set(i, nova_entrega);
                controle.SerializarXML(delivery_list, arquivo);
                return;
            }
        }
    }

    @Override
    public Entrega buscar(int id) {
        for (Entrega entrega : delivery_list) {
            if (entrega.getId() == id) {
                return entrega;
            }
        }
        return null;
    }

    @Override
    public List<Entrega> listar() {
        return delivery_list;
    }

    @Override
    public void atualizar() {
        controle.SerializarXML(delivery_list, arquivo);
    }
}
