package br.ufal.ic.p2.myfood.interfaces;

import java.util.List;

public interface Persistencia<T> {
    void salvarUsuario(T modelo) throws Exception ;

    void removerUsuario(String id) throws Exception ;

    void editarUsuario(String id, T novo_modelo) throws Exception ;

    T buscarUsuario(String id) throws Exception ;

    List<T> listarUsuarios();
}