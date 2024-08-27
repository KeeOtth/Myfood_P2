package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistenciaUsuarioEmMemoria implements Persistencia<Usuario> {

    private Map<String , Usuario> user_list = new HashMap<>();

    @Override
    public void salvarUsuario(Usuario usuario) throws Exception {
        if (user_list.containsKey(usuario.getLogin())) {
            throw new Exception("Conta com esse nome já existe.");
        } else if (usuario.getLogin() == null) {
            throw new Exception("Login inválido.");
        } else if (usuario.getSenha() == null) {
            throw new Exception("Senha inválida.");
        } else {
            user_list.put(usuario.getLogin(), usuario);
        }
    }

    @Override
    public void removerUsuario(String login) throws Exception {
        throw new Exception("Não implementada ainda (fodase)");
    }

    @Override
    public void editarUsuario(String login, Usuario updatedUser) throws Exception {
        if (!user_list.containsKey(login)){
            throw new Exception("Login não existe");
        }

        Usuario usuario = user_list.get(login);
        Map<String, String> aux = usuario.getAtributos();

        aux.putAll(updatedUser.getAtributos());

        usuario.setAtributos(aux);
    }

    @Override
    public Usuario buscarUsuario(String login) {
        return user_list.getOrDefault(login, null);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return List.of();
    }
}
