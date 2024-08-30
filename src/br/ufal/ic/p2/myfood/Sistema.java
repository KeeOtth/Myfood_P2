package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;
import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.*;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Sistema {

    public Persistencia<Usuario> persistenciaUsuario = new PersistenciaUsuario();
    public Persistencia<Empresa> persistenciaEmpresa = new PersistenciaEmpresa();
    public Persistencia<Produto> persistenciaProduto = new PersistenciaProduto();

    public Sistema() {
        persistenciaUsuario.iniciar();
        persistenciaEmpresa.iniciar();
        persistenciaProduto.iniciar();
        // Popular o ArrayList comp_list dos donos
        for (Usuario usuario : persistenciaUsuario.listar()) {
            if (usuario instanceof Dono dono) {
                List<Empresa> companiesOfUser = persistenciaEmpresa.listar()
                        .stream()
                        .filter(company -> company.getDono().getId() == dono.getId())
                        .toList();

                dono.setComp_list(companiesOfUser);
            }
        }
        for (Empresa comp : persistenciaEmpresa.listar()) {
           //TERMINAR ESSA PORRA AQUI!
            List<Produto> productsOfComp = persistenciaProduto.listar()
                    .stream()
                    .filter(produto -> produto.getId_dono() == comp.getId())
                    .toList();

            comp.setProd_list(productsOfComp);
        }
    }

    public void zerarSistema() {
        persistenciaUsuario.limpar();
        persistenciaEmpresa.limpar();
        persistenciaProduto.limpar();
    }

    public void encerrarSistema() {

    }

    private void testUserInvalid(String nome, String email, String senha, String endereco) throws UserCreationException {
        if (nome == null || nome.isEmpty()) {
            throw new UserCreationException("Nome invalido");
        }

        if (email == null || !(email.contains("@"))) {
            throw new UserCreationException("Email invalido");
        }

        if (senha == null || senha.isEmpty()) {
            throw new UserCreationException("Senha invalido");
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new UserCreationException("Endereco invalido");
        }

    }

    public void criarUsuario(String nome, String email, String senha, String endereco) 
            throws UserCreationException{

        testUserInvalid(nome, email, senha, endereco);

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)) {
                throw new UserCreationException("Conta com esse email ja existe");
            }
        }

        Usuario cliente = new Usuario(nome, email, senha, endereco);
        persistenciaUsuario.salvar(cliente);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws UserCreationException {

        testUserInvalid(nome, email, senha, endereco);

        if (cpf == null || cpf.length() != 14) {
            throw new UserCreationException("CPF invalido");
        }

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)) {
                throw new UserCreationException("Conta com ese email ja existe");
            }
        }

        Dono dono = new Dono(nome, email, senha, endereco, cpf);
        persistenciaUsuario.salvar(dono);
    }

    public String getAtributoUsuario(int id, String atributo) throws UnregisteredUserException {
        Usuario usuario = persistenciaUsuario.buscar(id);

        if (usuario == null)
            throw new UnregisteredUserException();

        return usuario.getAtributo(atributo);
    }

    public int login(String email, String senha) throws InvalidCredentialsException {
        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                return user.getId();
            }
        }
        throw new InvalidCredentialsException();
    }

    // Este método está apenas criando restaurantes
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha)
            throws CompanyCreationException, WrongTypeUserException{

        if (nome == null || nome.isEmpty()) {
            throw new CompanyCreationException("Nome invalido");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new CompanyCreationException("Endereco invalido");
        }

        for (Empresa empresa : persistenciaEmpresa.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() != dono) {
                throw new CompanyCreationException("Empresa com esse nome ja existe");
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new CompanyCreationException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        if (!(persistenciaUsuario.buscar(dono) instanceof Dono)) {
            throw new WrongTypeUserException();
        }

        if (tipoEmpresa.equals("restaurante")) {
            Dono tempDono = (Dono) persistenciaUsuario.buscar(dono);
            Restaurante restaurante = new Restaurante(nome, endereco, tempDono, tipoCozinha);
            persistenciaEmpresa.salvar(restaurante);
            tempDono.addComp_list(restaurante);
            return restaurante.getId();
        }

        return -1;
    }

    public String getEmpresasDoUsuario(int idDono) throws WrongTypeUserException {
        if (!(persistenciaUsuario.buscar(idDono) instanceof Dono tempDono)) {
            throw new WrongTypeUserException();
        }

        return "{" + tempDono.getComp_list().toString() + "}";
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws InvalidAtributeException, UnregisteredCompanyException {
        Empresa tempEmpresa = persistenciaEmpresa.buscar(empresa);
        if (tempEmpresa == null) {
            throw new UnregisteredCompanyException();
        }

        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAtributeException();
        }

        if (Objects.equals(atributo, "dono")) {
            Usuario tempUsuario = persistenciaUsuario.buscar(tempEmpresa.getDono().getId());
            return tempUsuario.getNome();
        }

        String result = tempEmpresa.getAtributo(atributo);
        if (result == null) {
            throw new InvalidAtributeException();
        }

        return result;
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws OutofBoundsException, WrongTypeUserException, UnregisteredCompanyException, CompanyCreationException {
        if (nome == null || nome.isEmpty()) {
            throw new CompanyCreationException("Nome invalido");
        }

        if (!(persistenciaUsuario.buscar(idDono) instanceof Dono)) {
            throw new WrongTypeUserException();
        }

        List<Empresa> companiesOfUser = persistenciaEmpresa.listar()
                .stream()
                .filter(company -> company.getDono().getId() == idDono && company.getNome().equals(nome))
                .toList();

        for (Empresa empresa : companiesOfUser) {
            if (!empresa.getNome().equals(nome)) {
                throw new UnregisteredCompanyException("Nao existe empresa com esse nome");
            }
        }

        if(getIndexByNome(companiesOfUser, nome) == -1) {
            throw new UnregisteredCompanyException("Nao existe empresa com esse nome");
        }

        if (indice >= companiesOfUser.size()) {
            throw new OutofBoundsException();
        } else if (indice < 0) {
            throw new OutofBoundsException("Indice invalido");
        }

        return companiesOfUser.get(indice).getId();
    }

    public static int getIndexByNome(List<Empresa> empresas, String nome) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getNome().equals(nome)) {
                return i;  // Retorna o índice da primeira ocorrência
            }
        }
        return -1;  // Retorna -1 se não encontrar
    }

    private void testProductInvalid(String nome, float valor, String categoria) throws ProductCreationException {
        if (nome == null || nome.isEmpty()) {
            throw new ProductCreationException("Nome invalido");
        }

        if (valor < 0) {
            throw new ProductCreationException("Valor invalido");
        }

        if (categoria == null || categoria.isEmpty()) {
            throw new ProductCreationException("Categoria invalido");
        }

    }

    public int criarProduto(int id_empresa, String nome, float valor, String categoria) throws ProductCreationException {
        Empresa comp = persistenciaEmpresa.buscar(id_empresa);

        testProductInvalid(nome, valor, categoria);

        for (Produto produto : comp.getProd_list()) {
            if (produto.getNome().equals(nome)){
                throw new ProductCreationException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        Produto produto = new Produto(nome, valor, categoria, comp.getId());
        persistenciaProduto.salvar(produto);
        comp.addProd_list(produto);
        return produto.getId();
    }

    public void editarProduto(int id_produto, String nome, float valor, String categoria) throws ProductCreationException {
        testProductInvalid(nome, valor, categoria);

        Produto prod = persistenciaProduto.buscar(id_produto);
        if (prod == null) {
            throw new ProductCreationException("Produto nao cadastrado");
        }
        persistenciaProduto.buscar(id_produto).setNome(nome);
        persistenciaProduto.buscar(id_produto).setValor(valor);
        persistenciaProduto.buscar(id_produto).setCategoria(categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws InvalidAtributeException, UnregisteredProductException {
        Empresa comp = persistenciaEmpresa.buscar(empresa);
        List<Produto> list = comp.getProd_list();

        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAtributeException();
        }

        for (Produto prod : list) {
            if (prod.getNome().equals(nome)) {
                return switch (atributo) {
                    case "nome" -> prod.getNome();
                    case "valor" -> String.format(Locale.US, "%.2f", prod.getValor());
                    case "categoria" -> prod.getCategoria();
                    case "empresa" -> String.valueOf(comp.getNome());
                    default -> throw new InvalidAtributeException("Atributo nao existe");
                };
            }
        }
        throw new UnregisteredProductException("Produto nao encontrado");
    }

    public String listarProdutos(int empresa) throws UnregisteredCompanyException {
        Empresa comp = persistenciaEmpresa.buscar(empresa);

        if (comp == null){
            throw new UnregisteredCompanyException("Empresa nao encontrada");
        }

        return "{" + comp.getProd_list() + "}";
    }
}
