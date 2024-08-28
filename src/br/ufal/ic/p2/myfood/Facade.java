package br.ufal.ic.p2.myfood;


import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.exceptions.*;

public class Facade {

    public Persistencia<Usuario> persistenciaUsuario = new PersistenciaUsuarioEmMemoria();
    public SerializacaoXML controle = new SerializacaoXML();

    public void zerarSistema() {
        controle.ApagarDadosXML("usuarios.xml");
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws InvalidEmailException,InvalidNameException, ExistingEmailException,InvalidAddressException, InvalidPasswordException{

        if (nome == null || nome.isEmpty()) {
            throw new InvalidNameException();
        }

        if (email == null || !(email.contains("@"))){
            throw new InvalidEmailException();
        }

        if (senha == null || senha.isEmpty()) {
            throw new InvalidPasswordException();
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new InvalidAddressException();
        }

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)){
                throw new ExistingEmailException();
            }
        }

        Cliente cliente = new Cliente(nome, email, senha, endereco);
        persistenciaUsuario.salvar(cliente);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws InvalidEmailException,InvalidNameException, ExistingEmailException,InvalidAddressException, InvalidCpfException, InvalidPasswordException{

        if (nome == null || nome.isEmpty()) {
            throw new InvalidNameException();
        }
        if (email == null || !(email.contains("@")) ){
            throw new InvalidEmailException();
        }
        if (senha == null || senha.isEmpty()) {
            throw new InvalidPasswordException();
        }
        if (cpf == null || cpf.length()!= 14){
            throw new InvalidCpfException();
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new InvalidAddressException();
        }

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)){
                throw new ExistingEmailException();
            }
        }

        Dono dono = new Dono(nome, email, senha, endereco, cpf);
        persistenciaUsuario.salvar(dono);
    }

    public String getAtributoUsuario(int id, String atributo)throws UnregisteredUserException{
        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getId() == id){
                return user.getAtributo(atributo);
            }
        }
        throw new UnregisteredUserException();
    }

    public int login(String email, String senha)throws InvalidLoginOrPasswordException{
        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)){
                return user.getId();
            }
        }
        throw new InvalidLoginOrPasswordException();
    }

    public void encerrarSistema() {
    }
}
