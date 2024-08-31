package br.ufal.ic.p2.myfood.interfaces;

import java.util.List;

public interface Persistencia<T> {
    void iniciar();

    void salvar(T modelo);

    void remover(int id);

    void limpar();

    void editar(T novo_modelo);

    T buscar(int id);

    List<T> listar();

    void atualizar();
}