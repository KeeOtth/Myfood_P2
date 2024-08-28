package br.ufal.ic.p2.myfood.models;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SerializacaoXML {

    public SerializacaoXML() {

    }

    public void SerializarXML(Object objeto, String nome_arquivo) {
        try (FileOutputStream fos = new FileOutputStream(nome_arquivo);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(objeto);
            System.out.println("Lista de Objetos serializada com sucesso!");
        } catch (IOException e) {
            System.out.println("Deu ruim!!");
        }

    }

    public <T> T DesserializarXML(String nome_arquivo) {
        T objeto = null;
        try (FileInputStream fis = new FileInputStream(nome_arquivo);
             XMLDecoder decoder = new XMLDecoder(fis)) {
            objeto = (T) decoder.readObject();
            System.out.println("Lista de objetos desserializada:");
        } catch (IOException e) {
            System.out.println("Deu ruim!!");
        }
        return objeto;
    }

    public void ApagarDadosXML(String nome_arquivo) {
        try (FileOutputStream fos = new FileOutputStream(nome_arquivo)) {
            // Escreve um conteúdo vazio no arquivo, efetivamente apagando todos os dados
            fos.write(new byte[0]);
            System.out.println("Todos os dados foram apagados do arquivo " + nome_arquivo);
        } catch (IOException e) {
            System.out.println("Deu ruim!!");
        }
    }
}
