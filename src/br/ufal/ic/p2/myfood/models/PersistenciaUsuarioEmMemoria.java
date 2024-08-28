package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.SerializacaoXML;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuarioEmMemoria implements Persistencia<Usuario>{

    private List<Usuario> user_list = new ArrayList<>();
    private SerializacaoXML controle = new SerializacaoXML();

    @Override
    public void salvar(Usuario user){
        user_list.add(user);
        controle.SerializarXML(user_list, "usuarios.xml");
    }

    @Override
    public void remover(int id) throws Exception {
        user_list.removeIf(user -> user.getId() == id);
        controle.SerializarXML(user_list, "usuarios.xml");
    }

    @Override
    public void editar(String id, Usuario novo_modelo) throws Exception {
        throw new Exception("ainda n fiz fodase");
    }

    @Override
    public Usuario buscar(int id) throws Exception {
        for (Usuario user : user_list) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listar() {
        return user_list;
    }

    public Usuario buscarEmail(String email) throws Exception {
        for (Usuario user : user_list) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

}