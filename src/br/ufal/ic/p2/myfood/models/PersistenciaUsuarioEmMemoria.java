package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.interfaces.Persistencia;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuarioEmMemoria implements Persistencia<Usuario>{

    private List<Usuario> user_list = new ArrayList<>();

    @Override
    public void salvar(Usuario user){
        user_list.add(user);
        SerializarXML();
    }

    @Override
    public void remover(int id) throws Exception {
        user_list.removeIf(user -> user.getId() == id);
        SerializarXML();
    }

    @Override
    public void editar(String id, Usuario novo_modelo) throws Exception {
        throw new Exception("ainda n fiz fodase");
    }

    @Override
    public Usuario buscar(int id) throws Exception {
        for (Usuario user : user_list) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listar() {
        return user_list;
    }

    public Usuario buscarEmail(String email) throws Exception {
        for (Usuario user : user_list) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void SerializarXML() {
        try (FileOutputStream fos = new FileOutputStream("usuarios.xml");
             XMLEncoder encoder = new XMLEncoder(fos)) {
                encoder.writeObject(user_list);
                System.out.println("Lista de Objetos serializada com sucesso!");
        } catch (IOException e) {
            System.out.println("Deu ruim!!");
        }

    }

    public void DesserializarXML() {
        try (FileInputStream fis = new FileInputStream("usuarios.xml");
             XMLDecoder decoder = new XMLDecoder(fis)) {
                user_list = (List<Usuario>) decoder.readObject();
                System.out.println("Lista de objetos desserializada:");
        } catch (IOException e) {
            System.out.println("Deu ruim!!");
        }
    }

    public void ApagarDadosXML() {
        String caminhoArquivo = "usuarios.xml";

        try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
            // Escreve um conteúdo vazio no arquivo, efetivamente apagando todos os dados
            fos.write(new byte[0]);
            System.out.println("Todos os dados foram apagados do arquivo " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Deu ruim!!");
        }
    }
}