package br.ufal.ic.p2.myfood.exceptions;

public class UnregisteredUserException extends Exception {
    public UnregisteredUserException() {
        super("Usuario nao cadastrado.");
    }

    public UnregisteredUserException(String message) {
        super(message);
    }
}