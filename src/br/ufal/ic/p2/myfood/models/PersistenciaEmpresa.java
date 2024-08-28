package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistenciaEmpresa implements Persistencia<Empresa> {


    private Map<Dono, List<Empresa>> comp_map = new HashMap<>();
    private SerializacaoXML controle = new SerializacaoXML();

    @Override
    public void salvar(Empresa modelo) {

    }

    public void salvar(Dono dono, Empresa modelo) {

    }

    @Override
    public void remover(int id) throws Exception {

    }

    @Override
    public void editar(String id, Empresa novo_modelo) throws Exception {

    }

    @Override
    public Empresa buscar(int id) throws Exception {
        return null;
    }

    @Override
    public List<Empresa> listar() {
        return List.of();
    }
}
