package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario implements Persistencia<Usuario> {

    private List<Usuario> user_list = new ArrayList<>();
    private SerializacaoXML controle = new SerializacaoXML();
    private final String arquivo = "xml/usuarios.xml";

    /**
     *  Desserializa o arquivo XML do Usuário e guardar na memória
     */
    @Override
    public void iniciar() {
        user_list = controle.DesserializarXML(user_list, arquivo);
    }

    @Override
    public void salvar(Usuario user) {
        user_list.add(user);
        controle.SerializarXML(user_list, arquivo);
    }

    @Override
    public void remover(int id){
        user_list.removeIf(user -> user.getId() == id);
        controle.SerializarXML(user_list, arquivo);
    }

    @Override
    public void limpar() {
        // Limpar a lista de usuários na memória
        if (user_list != null) {
            user_list.clear();
        }


        // Limpar a lista de usuários no XML
        controle.ApagarDadosXML(arquivo);
    }

    @Override
    public void editar(Usuario novo_usuario){
    }

    @Override
    public Usuario buscar(int id) {
        for (Usuario user : user_list) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listar() {
        return user_list;
    }

    @Override
    public void atualizar() {
        controle.SerializarXML(user_list, arquivo);
    }
}