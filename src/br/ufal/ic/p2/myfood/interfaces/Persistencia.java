package br.ufal.ic.p2.myfood.interfaces;

import java.util.List;

public interface Persistencia<T> {
    void iniciar(String caminho);

    void salvar(T modelo);

    void remover(int id) throws Exception;

    void limpar();

    void editar(String id, T novo_modelo) throws Exception;

    T buscar(int id);

    List<T> listar();
}