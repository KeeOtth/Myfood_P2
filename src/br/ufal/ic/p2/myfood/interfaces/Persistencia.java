package br.ufal.ic.p2.myfood.interfaces;

import java.util.List;

public interface Persistencia<T> {
    void salvar(T modelo);

    void remover(int id) throws Exception ;

    void editar(String id, T novo_modelo) throws Exception ;

    T buscar(int id) throws Exception ;

    List<T> listar();
}