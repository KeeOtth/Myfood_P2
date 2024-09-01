package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;
import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.persistence.*;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Sistema {

    /**
     * Estruturas de Persistência das Classes
     * Usuario, Esmpresa, Produto e Pedido
     */
    public static Persistencia<Usuario> persistenciaUsuario = new PersistenciaUsuario();
    public static Persistencia<Empresa> persistenciaEmpresa = new PersistenciaEmpresa();
    public static Persistencia<Produto> persistenciaProduto = new PersistenciaProduto();
    public static Persistencia<Pedido> persistenciaPedido = new PersistenciaPedido();

    /**
     * Inicia o Sistema. Carregando as informações dos XML para a memória
     */
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

    /**
     *  Limpa todos os dados do Sistema
     */
    public void zerarSistema() {
        persistenciaUsuario.limpar();
        persistenciaEmpresa.limpar();
        persistenciaProduto.limpar();
        persistenciaPedido.limpar();
    }

    /**
     * Encerra o sistema, salvando todos os dados nos XML
     */
    public void encerrarSistema() {
    }

    /**
     * Retorna a lista de Pedidos de um Cliente em uma determinada empresa
     * @param idCliente O id do cliente
     * @param idEmpresa O id da empresa
     * @return Os pedidos de um Cliente em uma determinada Empresa
     */
    public List<Pedido> pedidosClienteEmpresa(int idCliente, int idEmpresa) {
        String nomeCliente = persistenciaUsuario.buscar(idCliente).getNome();
        String nomeEmpresa = persistenciaEmpresa.buscar(idEmpresa).getNome();

        return persistenciaPedido.listar()
                .stream()
                .filter(pedido -> pedido.getCliente().getNome().equals(nomeCliente) && pedido.getEmpresa().getNome().equals(nomeEmpresa) && pedido.getEstado().equals("aberto"))
                .toList();
    }

    /**
     * Valida as informações para criar um usuário
     * @param nome O nome do usuário
     * @param email O email cadastrado
     * @param senha A senha cadastrada
     * @param endereco O endereco cadastrado
     * @throws UserCreationException Retorna o erro caso algum desses atributos forem nulos ou vazios
     */
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

    /**
     * Cria um usuário do tipo Cliente
     * @param nome O nome do cliente
     * @param email O email da conta do cliente
     * @param senha A senha da conta do cliente
     * @param endereco O endereco do cliente
     * @throws UserCreationException Retorna erro caso algum desses atributos forem nulo ou vazio, ou caso já exista uma conta
     */
    public void criarUsuario(String nome, String email, String senha, String endereco) throws UserCreationException{

        testUserInvalid(nome, email, senha, endereco);

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)) {
                throw new UserCreationException("Conta com esse email ja existe");
            }
        }

        Usuario cliente = new Usuario(nome, email, senha, endereco);
        persistenciaUsuario.salvar(cliente);
    }

    /**
     * Cria um usuário do tipo Dono
     * @param nome O nome do dono da empresa
     * @param email O email da conta do dono da empresa
     * @param senha A senha da conta do dono da empresa
     * @param endereco O endereco do dono da empresa
     * @param cpf O CPF do dono da empresa
     * @throws UserCreationException Retorna erro caso algum desses atributos forem nulo ou vazio, ou caso já exista uma conta
     */
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws UserCreationException {

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

    /**
     * Retorna uma string contendo o valor do atributo informado
     * @param id O id do usuário
     * @param atributo O nome do atributo que deseja
     * @return O atributo
     * @throws UnregisteredException Retorna um erro, caso não encontre o usuário
     */
    public String getAtributoUsuario(int id, String atributo) throws UnregisteredException {
        Usuario usuario = persistenciaUsuario.buscar(id);

        if (usuario == null)
            throw new UnregisteredException("Usuario nao cadastrado.");

        return usuario.getAtributo(atributo);
    }

    /**
     * Valida as credencias do usuário
     * @param email Uma String contendo o email do usuário
     * @param senha Uma String contendo a senha do usuário
     * @return Retorna o id do usuário
     * @throws InvalidCredentialsException Retorna um erro, caso as credenciais não estejam corretas
     */
    public int login(String email, String senha) throws InvalidCredentialsException {
        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                return user.getId();
            }
        }
        throw new InvalidCredentialsException();
    }

    /**
     * Cria um Empresa de um determinado tipo para um determinado Dono
     * @param tipoEmpresa O tipo de empresa - Atualmente está apenas criando do tipo Restaurante
     * @param dono O id do Dono da empresa
     * @param nome O nome da empresa
     * @param endereco O endereco da empresa
     * @param tipoCozinha O estilo de comida ofertada pelo restaurante
     * @return O Id da empresa
     * @throws CompanyCreationException
     * @throws WrongTypeUserException
     */
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws CompanyCreationException, WrongTypeUserException{

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

        if (!(persistenciaUsuario.buscar(dono).getClass().getSimpleName().equals("Dono"))) {
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

    /**
     * Retorna a lista de empresas de um Dono
     * @param idDono O id do usuário desejado
     * @return Uma String contendo a lista de empresas do Dono
     * @throws WrongTypeUserException Retorna erro, caso o id passado não seja do tipo Dono
     */
    public String getEmpresasDoUsuario(int idDono) throws WrongTypeUserException {
        if (!(persistenciaUsuario.buscar(idDono).getClass().getSimpleName().equals("Dono"))) {
            throw new WrongTypeUserException();
        }

        Dono tempDono = (Dono) persistenciaUsuario.buscar(idDono);
        return "{" + tempDono.getComp_list().toString() + "}";
    }

    /**
     * Retorna um determinado atributo de uma determinada empresa
     * @param idEmpresa O id da empresa desejada
     * @param atributo O atributo desejado
     * @return Uma String contendo o valor do atributo desejado
     * @throws InvalidAtributeException Retorna um erro caso o atributo informado seja inválido
     * @throws UnregisteredException  Retorna um erro caso a empresa não esteja cadastrada
     */
    public String getAtributoEmpresa(int idEmpresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        Empresa tempEmpresa = persistenciaEmpresa.buscar(idEmpresa);
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

    /**
     * Retorna o id de uma empresa de um determinado produto
     * @param idDono O id do dono das empresas
     * @param nome O nome da empresa
     * @param indice O indice da empresa desejada na lista de empresas do determinado dono
     * @return O id da empresa desejada
     * @throws OutofBoundsException Retorna erro caso o indice informado esteja além dos limites da lista
     * @throws WrongTypeUserException Retorna erro caso o id informado não seja do tipo Dono
     * @throws UnregisteredException Retorna erro caso não a empresa informada não exista
     * @throws CompanyCreationException Retorna erro caso o nome informado seja inválido
     */
    public int getIdEmpresa(int idDono, String nome, int indice) throws OutofBoundsException, WrongTypeUserException, UnregisteredException, CompanyCreationException {
        if (nome == null || nome.isEmpty()) {
            throw new CompanyCreationException("Nome invalido");
        }

        if (!(persistenciaUsuario.buscar(idDono).getClass().getSimpleName().equals("Dono"))) {
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

    /**
     * Retorna o id da empresa desejada
     * @param empresas A lista das empresas
     * @param nome O nome da empresa que deseja buscar
     * @return O id da empresa, caso não encontre retorna -1
     */
    public static int getIndexByNome(List<Empresa> empresas, String nome) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getNome().equals(nome)) {
                return i;  // Retorna o índice da primeira ocorrência
            }
        }
        return -1;  // Retorna -1 se não encontrar
    }

    /**
     * Valida os atributos para a criação do Produto
     * @param nome O nome do produto
     * @param valor O valor do produto
     * @param categoria A categoria do produto
     * @throws ProductCreationException Retorna um erro caso algum atributo seja inválido
     */
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

    /**
     * Cria um produto para uma determinada empresa
     * @param idEmpresa O id da empresa
     * @param nome O nome do produto
     * @param valor O valor do produto
     * @param categoria A categoria do produto
     * @return O id do produto cadastrado
     * @throws ProductCreationException Retorna erro caso algum atributo seja inválido
     */
    public int criarProduto(int idEmpresa, String nome, float valor, String categoria) throws ProductCreationException {
        Empresa comp = persistenciaEmpresa.buscar(idEmpresa);

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

    /**
     * Edita os atributos de um produto
     * @param idProduto O id do produto que vai ser editado
     * @param nome O novo nome a ser atribuido
     * @param valor O novo valor a ser atribuido
     * @param categoria A novo categoria a ser atribuida
     * @throws ProductCreationException Retorna erro caso o produto seja nulo
     */
    public void editarProduto(int idProduto, String nome, float valor, String categoria) throws ProductCreationException {
        testProductInvalid(nome, valor, categoria);

        Produto prod = persistenciaProduto.buscar(idProduto);
        if (prod == null) {
            throw new ProductCreationException("Produto nao cadastrado");
        }

        prod.setNome(nome);
        prod.setValor(valor);
        prod.setCategoria(categoria);

        persistenciaProduto.editar(prod);
    }

    /**
     * Retorna o valor do atributo de um Produto desejado
     * @param nome O nome do produto
     * @param idEmpresa O id da empresa onde o produto está cadastrado
     * @param atributo O nome do atributo que deseja obter
     * @return O valor do atributo do produto
     * @throws InvalidAtributeException Retorna erro caso o atributo informado seja inválido
     * @throws UnregisteredException Retorna erro caso o produto desejado não esteja caastrado
     */
    public String getProduto(String nome, int idEmpresa, String atributo) throws InvalidAtributeException, UnregisteredException {
        Empresa comp = persistenciaEmpresa.buscar(idEmpresa);
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

    /**
     * Retorna uma String com todos os produtos de uma empresa
     * @param idEmpresa O id da empresa que deseja consultar
     * @return Uma String contendo a lista dos produtos de uma empresa
     * @throws UnregisteredException
     */
    public String listarProdutos(int idEmpresa) throws UnregisteredException {
        Empresa comp = persistenciaEmpresa.buscar(idEmpresa);

        if (comp == null){
            throw new UnregisteredException("Empresa nao encontrada");
        }

        return "{" + comp.getProd_list() + "}";
    }

    /**
     * Cria um pedido de um determinado Cliente em uma determinada Empresa
     * @param idCliente O id do Cliente
     * @param idEmpresa O id da Empresa
     * @return O id do pedido criado
     * @throws OrderCreationException Retorna erro caso um usuário do tipo Cliente tente criar um pedido quando tem um pedido em aberto na mesma empresa
     * @throws WrongTypeUserException Retorna erro caso um usuário do tipo Dono tente criar um pedido
     */
    public int criarPedido(int idCliente, int idEmpresa) throws OrderCreationException, WrongTypeUserException {
        Usuario temp_cliente = persistenciaUsuario.buscar(idCliente);
        List<Pedido> pedidosClienteEmpresa = pedidosClienteEmpresa(idCliente, idEmpresa);

        if (temp_cliente.getClass().getSimpleName().equals("Dono")){
            throw new WrongTypeUserException("Dono de empresa nao pode fazer um pedido");
        } else if (!pedidosClienteEmpresa.isEmpty()) {
            throw new OrderCreationException();
        } else {
            Empresa temp_comp = persistenciaEmpresa.buscar(idEmpresa);
            Pedido ped = new Pedido(temp_cliente, temp_comp);
            persistenciaPedido.salvar(ped);
            return ped.getNumero();
        }
    }

    /**
     * Adiciona um produto no pedido
     * @param idPedido
     * @param idProduto
     * @throws UnregisteredException Retorna erro caso o produto desejado não pertença a empresa
     * @throws StatusException Retorna erro caso o pedido esteja em aberto
     */
    public void adicionarProduto(int idPedido, int idProduto) throws UnregisteredException, StatusException {
        Pedido tempPedido = persistenciaPedido.buscar(idPedido);

        if (tempPedido == null) {
            throw new UnregisteredException("Nao existe pedido em aberto");
        }

        if (!(tempPedido.getEstado().equals("aberto"))){
            throw new StatusException("Nao e possivel adcionar produtos a um pedido fechado");
        }

        List<Produto> prodList = tempPedido.getEmpresa().getProd_list();
        for (Produto prod : prodList) {
            if (prod.getId() == idProduto) {
                tempPedido.addProductToList(prod);
                return;
            }
        }

        throw new UnregisteredException("O produto nao pertence a essa empresa");
    }

    /**
     * Retorna o id de um pedido do historico de um cliente em uma empresa
     * @param idCliente
     * @param idEmpresa
     * @param indice O indice do pedido desejada na lista de pedidos do determinado cliente em uma determinada empresa
     * @return O id do pedido desejado
     */
    public int getNumeroPedido(int idCliente, int idEmpresa, int indice) {
        String nomeCliente = persistenciaUsuario.buscar(idCliente).getNome();
        String nomeEmpresa = persistenciaEmpresa.buscar(idEmpresa).getNome();

        List<Pedido> pedidosClienteEmpresa = persistenciaPedido.listar()
                        .stream()
                        .filter(pedido -> pedido.getCliente().getNome().equals(nomeCliente) && pedido.getEmpresa().getNome().equals(nomeEmpresa))
                        .toList();

        return pedidosClienteEmpresa.get(indice).getNumero();
    }

    /**
     * Retorna o valor do atributo do pedido
     * @param idPedido
     * @param atributo
     * @return O valor do atributo desejado
     * @throws UnregisteredException
     * @throws InvalidAtributeException
     */
    public String getPedidos(int idPedido, String atributo) throws UnregisteredException, InvalidAtributeException {
        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAtributeException();
        }

        Pedido tempPedido = persistenciaPedido.buscar(idPedido);

        if (tempPedido.getNumero() == idPedido) {
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

    /**
     * Muda o status do pedido para "preparando"
     * @param idPedido
     * @throws UnregisteredException Retorna erro caso o pedido informado não esteja cadastrado
     */
    public void fecharPedido(int idPedido) throws UnregisteredException {
        Pedido tempPedido = persistenciaPedido.buscar(idPedido);
        if (tempPedido == null) {
            throw new UnregisteredException("Pedido nao encontrado");
        }

        tempPedido.changeEstado();

    }

    /**
     * Remove o produto em um pedido
     * @param idPedido
     * @param produto O nome do produto
     * @throws InvalidAtributeException Retorna erro caso o atributo seja nulo ou vazio
     * @throws UnregisteredException Retorna erro caso o produto informado não está cadastrado
     * @throws StatusException Retorna erro caso esteja tentando remover produto de um pedido fechado
     */
    public void removerProduto(int idPedido, String produto) throws InvalidAtributeException, UnregisteredException, StatusException {
        if (produto == null || produto.isEmpty()) {
            throw new InvalidAtributeException("Produto invalido");
        }

        Pedido ped = persistenciaPedido.buscar(idPedido);
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
