package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;
import br.ufal.ic.p2.myfood.interfaces.Persistencia;
import br.ufal.ic.p2.myfood.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Facade {

    public Persistencia<Usuario> persistenciaUsuario = new PersistenciaUsuario();
    public Persistencia<Empresa> persistenciaEmpresa = new PersistenciaEmpresa();

    public Facade() {
        persistenciaUsuario.iniciar("usuario.xml");
        persistenciaEmpresa.iniciar("empresa.xml");

        // Popular o ArrayList comp_list dos donos
        for (Usuario usuario : persistenciaUsuario.listar()) {
            if (usuario instanceof Dono) {
                Dono dono = (Dono) usuario;

                List<Empresa> companiesOfUser = persistenciaEmpresa.listar()
                        .stream()
                        .filter(company -> company.getId_dono() == dono.getId())
                        .toList();

                dono.setComp_list(companiesOfUser);

            }
        }

    }

    public void zerarSistema() {
        persistenciaUsuario.limpar();
        persistenciaEmpresa.limpar();
    }

    private void testInvalid(String nome, String email, String senha, String endereco) throws InvalidNameException, InvalidEmailException, InvalidPasswordException, InvalidAddressException {
        if (nome == null || nome.isEmpty()) {
            throw new InvalidNameException();
        }

        if (email == null || !(email.contains("@"))) {
            throw new InvalidEmailException();
        }

        if (senha == null || senha.isEmpty()) {
            throw new InvalidPasswordException();
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new InvalidAddressException();
        }

    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws InvalidEmailException, InvalidNameException, ExistingEmailException, InvalidAddressException, InvalidPasswordException {

        testInvalid(nome, email, senha, endereco);

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)) {
                throw new ExistingEmailException();
            }
        }

        Cliente cliente = new Cliente(nome, email, senha, endereco);
        persistenciaUsuario.salvar(cliente);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws InvalidEmailException, InvalidNameException, ExistingEmailException, InvalidAddressException, InvalidCpfException, InvalidPasswordException {

        testInvalid(nome, email, senha, endereco);

        if (cpf == null || cpf.length() != 14) {
            throw new InvalidCpfException();
        }

        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email)) {
                throw new ExistingEmailException();
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

    public int login(String email, String senha) throws InvalidLoginOrPasswordException {
        for (Usuario user : persistenciaUsuario.listar()) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                return user.getId();
            }
        }
        throw new InvalidLoginOrPasswordException();
    }

    // Este m�todo est� apenas criando restaurantes
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha)
            throws RepeatCompanyException, WrongTypeUserException, InvalidNameException, InvalidAddressException {

        if (nome == null || nome.isEmpty()) {
            throw new InvalidNameException();
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new InvalidAddressException();
        }

        for (Empresa empresa : persistenciaEmpresa.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getId_dono() != dono) {
                throw new RepeatCompanyException();
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new RepeatCompanyException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        if (persistenciaUsuario.buscar(dono) instanceof Cliente) {
            throw new WrongTypeUserException();
        }

        if (tipoEmpresa.equals("restaurante")) {
            Dono tempDono = (Dono) persistenciaUsuario.buscar(dono);
            Restaurante restaurante = new Restaurante(nome, endereco, dono, tipoCozinha);
            persistenciaEmpresa.salvar(restaurante);
            tempDono.addComp_list(restaurante);
            return restaurante.getId();
        }

        return -1;
    }

    public String getEmpresasDoUsuario(int idDono) throws WrongTypeUserException {
        if (persistenciaUsuario.buscar(idDono) instanceof Cliente) {
            throw new WrongTypeUserException();
        }
        Dono tempDono = (Dono) persistenciaUsuario.buscar(idDono);

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
            Usuario tempUsuario = persistenciaUsuario.buscar(tempEmpresa.getId_dono());
            return tempUsuario.getNome();
        }

        String result = tempEmpresa.getAtributo(atributo);
        if (result == null) {
            throw new InvalidAtributeException();
        }

        return result;
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws OutofBoundsException, InvalidNameException, WrongTypeUserException, UnregisteredCompanyException {
        if (nome == null || nome.isEmpty()) {
            throw new InvalidNameException();
        }

        if (persistenciaUsuario.buscar(idDono) instanceof Cliente) {
            throw new WrongTypeUserException();
        }

        List<Empresa> companiesOfUser = persistenciaEmpresa.listar()
                                                            .stream()
                                                            .filter(company -> company.getId_dono() == idDono && company.getNome().equals(nome))
                                                            .toList();

//        System.out.println(nome + " " + companiesOfUser);

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

//        System.out.println(nome + " " + companiesOfUser.get(indice).getId());

        return companiesOfUser.get(indice).getId();
    }

    public static int getIndexByNome(List<Empresa> empresas, String nome) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getNome().equals(nome)) {
                return i;  // Retorna o �ndice da primeira ocorr�ncia
            }
        }
        return -1;  // Retorna -1 se n�o encontrar
    }

    public int criarProduto(int id_empresa, String nome, float valor, String categoria) {
        Produto produto = new Produto(nome, valor, categoria);


        return 1;
    }

    public void encerrarSistema() {
    }
}
