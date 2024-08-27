package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.exceptions.*;

import java.util.List;

public class Facade {
    public void zerarSistema() {

    }

    public Persistencia<Usuario> persistenciaUsuario = new PersistenciaUsuarioEmMemoria();

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws InvalidEmailException,InvalidNameException, ExistingEmailException,InvalidAddressException{

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)){
                throw new ExistingEmailException();
            }
        }
        if (email == null || !(email.contains("@"))){
            throw new InvalidEmailException();
        }
        if (nome == null || nome.isEmpty()) {
            throw new InvalidNameException();
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new InvalidAddressException();
        }

        Cliente cliente = new Cliente(nome, email, senha, endereco);
        persistenciaUsuario.salvar(cliente);
        List<Usuario> temp = persistenciaUsuario.listar();
        for (Usuario user : temp) {
            System.out.println(user);
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws InvalidEmailException,InvalidNameException, ExistingEmailException,InvalidAddressException, InvalidCpfException{

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)){
                throw new ExistingEmailException();
            }
        }
        if (cpf == null || cpf.length()!= 14){
            throw new InvalidEmailException();
        }
        if (email == null || !(email.contains("@"))){
            throw new InvalidEmailException();
        }
        if (nome == null || nome.isEmpty()) {
            throw new InvalidNameException();
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new InvalidAddressException();
        }
        Dono dono = new Dono(nome, email, senha, endereco, cpf);
        persistenciaUsuario.salvar(dono);

        List<Usuario> temp = persistenciaUsuario.listar();
        for (Usuario user : temp) {
            System.out.println(user);
        }
    }
    //Aprender a usar o try/catch para lidar com exceções.
}
