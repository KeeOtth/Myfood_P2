package br.ufal.ic.p2.myfood.exceptions;

public class InvalidAtributeException extends Exception {
    public InvalidAtributeException() {
        super("Atributo invalido");
    }

    public InvalidAtributeException(String message) {
        super(message);
    }
}