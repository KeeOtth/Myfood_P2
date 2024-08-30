package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;

public class Facade {
    private Sistema sys = new Sistema();

    public void zerarSistema(){
        sys.zerarSistema();
    }

    public void encerrarSistema(){
        sys.encerrarSistema();
    }

    // User Story 1

    public String getAtributoUsuario(int id, String atributo) throws UnregisteredUserException {
        return sys.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws UserCreationException {
        sys.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws UserCreationException {
        sys.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws InvalidCredentialsException {
        return sys.login(email, senha);
    }

    // User Story 2

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws CompanyCreationException, WrongTypeUserException {
        return sys.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(int dono) throws WrongTypeUserException {
        return sys.getEmpresasDoUsuario(dono);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws InvalidAtributeException, UnregisteredCompanyException {
        return sys.getAtributoEmpresa(empresa, atributo);
    }

    public int getIdEmpresa(int idDono, String nome, int indice)  throws OutofBoundsException, WrongTypeUserException, UnregisteredCompanyException, CompanyCreationException {
        return sys.getIdEmpresa(idDono, nome, indice);
    }

    // User Story 3

    public int criarProduto(int id_empresa, String nome, float valor, String categoria) throws ProductCreationException {
        return sys.criarProduto(id_empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws ProductCreationException {
        sys.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws InvalidAtributeException, UnregisteredProductException {
        return sys.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws UnregisteredCompanyException {
        return sys.listarProdutos(empresa);
    }

    // User Story 4

}