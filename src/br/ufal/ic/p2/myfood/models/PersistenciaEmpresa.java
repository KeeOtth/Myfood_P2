package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaEmpresa implements Persistencia<Empresa> {

    private List<Empresa> comp_list = new ArrayList<>();
    private SerializacaoXML controle = new SerializacaoXML();

    @Override
    public void iniciar(String caminho) {
        comp_list = controle.DesserializarXML(comp_list, "empresas.xml");
    }

    @Override
    public void salvar(Empresa modelo) {
        comp_list.add(modelo);
        controle.SerializarXML(modelo, "empresas.xml");
    }

    @Override
    public void remover(int id) throws Exception {
        comp_list.removeIf(comp -> comp.getId() == id);
        controle.SerializarXML(comp_list, "empresas.xml");
    }

    @Override
    public void limpar() {
        // Limpar a lista de empresas na memória
        if (comp_list != null){
            comp_list.clear();
        }

        // Limpar a lista de empresas no XML
        controle.ApagarDadosXML("empresas.xml");
    }

    @Override
    public void editar(String id, Empresa novo_modelo) throws Exception {
        throw new Exception("ainda n fiz fodase");
    }

    @Override
    public Empresa buscar(int id){
        for (Empresa comp : comp_list) {
            if(comp.getId() == id) {
                return comp;
            }
        }
        return null;
    }

    @Override
    public List<Empresa> listar() {
        return comp_list;
    }
}
