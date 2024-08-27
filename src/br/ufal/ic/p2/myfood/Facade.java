package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.PersistenciaUsuarioEmMemoria;
import br.ufal.ic.p2.myfood.models.Usuario;

public class Facade {
    public void zerarSistema() {

    }

    public Persistencia<Usuario> persistenciaUsuario = new PersistenciaUsuarioEmMemoria();

    public String getAtributoUsuario(String login, String atributo) throws Exception {
        Usuario user = persistenciaUsuario.buscarUsuario(login);
        if (user != null && atributo.equals("nome")) {
            return user.getNome();
        } else if (user != null && atributo.equals("senha")) {
            return user.getSenha();
        }
        throw new Exception("Usuário não cadastrado.");
    }

    public void criarUsuario(String login, String senha, String nome) throws Exception {
        Usuario novo_usuario = new Usuario(login, senha, nome);
        persistenciaUsuario.salvarUsuario(novo_usuario);
    }

    public int abrirSessao(String login, String senha) throws Exception {
        Usuario user = persistenciaUsuario.buscarUsuario(login);

        if (user == null || !user.getSenha().equals(senha)) {
            throw new Exception("Login ou senha inválidos.");
        }

        return 1;
    }

    public void encerrarSistema() {

    }

    public void editarPerfil(int id, String atributo, String valor) throws Exception {

    }
    //Aprender a usar o try/catch para lidar com exceções.
}
