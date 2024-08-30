package br.ufal.ic.p2.myfood.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Login ou senha invalidos");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}