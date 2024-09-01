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
    public Persistencia<Pedido> persistenciaPedido = new PersistenciaPedido();

    public Sistema() {
        persistenciaUsuario.iniciar();
        persistenciaEmpresa.iniciar();
        persistenciaProduto.iniciar();
        persistenciaPedido.iniciar();

        // Carregar o ArrayList comp_list dos donos
        for (Usuario usuario : persistenciaUsuario.listar()) {
            if (usuario instanceof Dono dono) {
                List<Empresa> companiesOfUser = persistenciaEmpresa.listar()
                        .stream()
                        .filter(company -> company.getDono().getId() == dono.getId())
                        .toList();

                dono.setComp_list(companiesOfUser);
            }
        }

        // Carregar o ArrayList prod_list das empresas
        for (Empresa comp : persistenciaEmpresa.listar()) {
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
        persistenciaPedido.limpar();
    }

    public void encerrarSistema() {

    }

    public List<Pedido> pedidosClienteEmpresa(int cliente, int empresa) {
        String nomeCliente = persistenciaUsuario.buscar(cliente).getNome();
        String nomeEmpresa = persistenciaEmpresa.buscar(empresa).getNome();

        return persistenciaPedido.listar()
                .stream()
                .filter(pedido -> pedido.getCliente().getNome().equals(nomeCliente) && pedido.getEmpresa().getNome().equals(nomeEmpresa) && pedido.getEstado().equals("aberto"))
                .toList();
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

    public String getAtributoUsuario(int id, String atributo) throws UnregisteredException {
        Usuario usuario = persistenciaUsuario.buscar(id);

        if (usuario == null)
            throw new UnregisteredException("Usuario nao cadastrado.");

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

    //OBS: Este método está apenas criando restaurantes
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

    public String getAtributoEmpresa(int empresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        Empresa tempEmpresa = persistenciaEmpresa.buscar(empresa);
        if (tempEmpresa == null) {
            throw new UnregisteredException("Empresa nao cadastrada");
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

    public int getIdEmpresa(int idDono, String nome, int indice) throws OutofBoundsException, WrongTypeUserException, UnregisteredException, CompanyCreationException {
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
                throw new UnregisteredException("Nao existe empresa com esse nome");
            }
        }

        if(getIndexByNome(companiesOfUser, nome) == -1) {
            throw new UnregisteredException("Nao existe empresa com esse nome");
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

        prod.setNome(nome);
        prod.setValor(valor);
        prod.setCategoria(categoria);

        persistenciaProduto.editar(prod);
    }

    public String getProduto(String nome, int empresa, String atributo) throws InvalidAtributeException, UnregisteredException {
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
        throw new UnregisteredException("Produto nao encontrado");
    }

    public String listarProdutos(int empresa) throws UnregisteredException {
        Empresa comp = persistenciaEmpresa.buscar(empresa);

        if (comp == null){
            throw new UnregisteredException("Empresa nao encontrada");
        }

        return "{" + comp.getProd_list() + "}";
    }

    public int criarPedido(int cliente, int empresa) throws Exception, WrongTypeUserException {
        Usuario temp_cliente = persistenciaUsuario.buscar(cliente);
        List<Pedido> pedidosClienteEmpresa = pedidosClienteEmpresa(cliente, empresa);

        if (temp_cliente instanceof Dono){
            throw new WrongTypeUserException("Dono de empresa nao pode fazer um pedido");
        } else if (!pedidosClienteEmpresa.isEmpty()) {
            throw new Exception("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
        } else {
            Empresa temp_comp = persistenciaEmpresa.buscar(empresa);
            Pedido ped = new Pedido(temp_cliente, temp_comp);
            persistenciaPedido.salvar(ped);
            return ped.getNumero();
        }
    }

    public void adicionarProduto(int pedido, int produto) throws UnregisteredException, StatusException {
        Pedido tempPedido = persistenciaPedido.buscar(pedido);

        if (tempPedido == null) {
            throw new UnregisteredException("Nao existe pedido em aberto");
        }

        if (!(tempPedido.getEstado().equals("aberto"))){
            throw new StatusException("Nao e possivel adcionar produtos a um pedido fechado");
        }

        List<Produto> prodList = tempPedido.getEmpresa().getProd_list();
        for (Produto prod : prodList) {
            if (prod.getId() == produto) {
                tempPedido.addProductToList(prod);
                return;
            }
        }

        throw new UnregisteredException("O produto nao pertence a essa empresa");
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) {
        String nomeCliente = persistenciaUsuario.buscar(cliente).getNome();
        String nomeEmpresa = persistenciaEmpresa.buscar(empresa).getNome();

        List<Pedido> pedidosClienteEmpresa = persistenciaPedido.listar()
                        .stream()
                        .filter(pedido -> pedido.getCliente().getNome().equals(nomeCliente) && pedido.getEmpresa().getNome().equals(nomeEmpresa))
                        .toList();

        return pedidosClienteEmpresa.get(indice).getNumero();
    }

    public String getPedidos(int pedido, String atributo) throws UnregisteredException, InvalidAtributeException {
        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAtributeException();
        }

        Pedido tempPedido = persistenciaPedido.buscar(pedido);

        if (tempPedido.getNumero() == pedido) {
            return switch (atributo) {
                case "cliente" -> tempPedido.getCliente().getNome();
                case "empresa" -> tempPedido.getEmpresa().getNome();
                case "estado" -> tempPedido.getEstado();
                case "produtos" -> "{" + tempPedido.getProd_list() + "}";
                case "valor" -> String.format(Locale.US, "%.2f", tempPedido.getValor_total());
                default -> throw new InvalidAtributeException("Atributo nao existe");
            };
        }
        throw new UnregisteredException("Produto nao encontrado");
    }

    public void fecharPedido(int pedido) throws UnregisteredException {
        Pedido tempPedido = persistenciaPedido.buscar(pedido);
        if (tempPedido == null) {
            throw new UnregisteredException("Pedido nao encontrado");
        }

        tempPedido.changeEstado();

    }

    public void removerProduto(int pedido, String produto) throws InvalidAtributeException, UnregisteredException, StatusException {
        if (produto == null || produto.isEmpty()) {
            throw new InvalidAtributeException("Produto invalido");
        }

        Pedido ped = persistenciaPedido.buscar(pedido);
        if (ped.getEstado().equals("preparando")) {
            throw new StatusException("Nao e possivel remover produtos de um pedido fechado");
        }

        List<Produto> listProd = ped.getProd_list();
        for (Produto prod : listProd) {
            if (prod.getNome().equals(produto)){
                    ped.removeProductFromList(prod);
                    persistenciaPedido.atualizar();
                return;
            }
        }

        throw new UnregisteredException("Produto nao encontrado");
    }
}
