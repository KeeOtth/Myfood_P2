package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;

public class Facade {
    private final Sistema sys = new Sistema();

    public void zerarSistema() {
        sys.zerarSistema();
    }

    public void encerrarSistema() {
        sys.encerrarSistema();
    }

    // User Story 1

    public String getAtributoUsuario(int id, String atributo) throws UnregisteredException {
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

    public String getAtributoEmpresa(int empresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        return sys.getAtributoEmpresa(empresa, atributo);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws OutofBoundsException, WrongTypeUserException, UnregisteredException, CompanyCreationException {
        return sys.getIdEmpresa(idDono, nome, indice);
    }

    // User Story 3

    public int criarProduto(int id_empresa, String nome, float valor, String categoria) throws ProductCreationException {
        return sys.criarProduto(id_empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws ProductCreationException {
        sys.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        return sys.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws UnregisteredException {
        return sys.listarProdutos(empresa);
    }

    // User Story 4

    public int criarPedido(int cliente, int empresa) throws Exception, WrongTypeUserException {
        return sys.criarPedido(cliente, empresa);
    }

    public int getNumeroPedido(int cleinte, int empresa, int indice) {
        return sys.getNumeroPedido(cleinte, empresa, indice);
    }

    public void adicionarProduto(int pedido, int produto) throws UnregisteredException, StatusException {
        sys.adicionarProduto(pedido, produto);
    }

    public String getPedidos(int pedido, String atributo) throws InvalidAtributeException, UnregisteredException {
        return sys.getPedidos(pedido, atributo);
    }

    public void fecharPedido(int pedido) throws UnregisteredException {
        sys.fecharPedido(pedido);
    }

    public void removerProduto(int pedido, String produto) throws StatusException, UnregisteredException, InvalidAtributeException {
        sys.removerProduto(pedido, produto);
    }

    // User Story 5

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws CompanyCreationException, WrongTypeUserException{
        return sys.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws CompanyCreationException {
        sys.alterarFuncionamento(mercado, abre, fecha);
    }

    // User Story 6

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, Boolean aberto24Horas, int numeroFuncionarios) throws CompanyCreationException, WrongTypeUserException{
        return sys.criarEmpresa(tipoEmpresa, dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    // User Story 7
    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws UserCreationException{
        sys.criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }

    public void cadastrarEntregador(int empresa, int entregador) throws UserCreationException, WrongTypeUserException {
        sys.cadastrarEntregador(empresa, entregador);
    }

    public String getEntregadores(int empresa) throws UnregisteredException {
        return sys.getEntregadores(empresa);
    }

    public String getEmpresas(int entregador) throws WrongTypeUserException {
        return sys.getEmpresas(entregador);
    }
}