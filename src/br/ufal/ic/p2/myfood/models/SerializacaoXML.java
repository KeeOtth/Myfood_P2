package br.ufal.ic.p2.myfood.models;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SerializacaoXML {

    public SerializacaoXML() {

    }

    public void SerializarXML(Object objeto, String nome_arquivo) {
        try (FileOutputStream fos = new FileOutputStream(nome_arquivo);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(objeto);
            System.out.println("Objetos de " + nome_arquivo + " foram serializados");
        } catch (IOException e) {
            System.out.println("Deu ruim!!");
        }

    }

    public <T> T DesserializarXML(T objeto, String nome_arquivo) {
        File file = new File(nome_arquivo);

        // Verificar se o arquivo existe e se está vazio
        if (!file.exists()) {
            System.out.println(nome_arquivo + " não existe.");
            return objeto;
        } else if (file.length() == 0) {
            System.out.println(nome_arquivo + " está vazio.");
            return objeto;
        }

        try (FileInputStream fis = new FileInputStream(nome_arquivo);
             XMLDecoder decoder = new XMLDecoder(fis)) {
            objeto = (T) decoder.readObject();
            System.out.println("Objetos de " + nome_arquivo + " foram desserializados");
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
