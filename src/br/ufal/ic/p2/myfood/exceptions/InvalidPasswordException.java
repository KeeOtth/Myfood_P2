package br.ufal.ic.p2.myfood.exceptions;

public class InvalidPasswordException extends Exception{
    public InvalidPasswordException() {
        super("Senha invalido");
    }
    public InvalidPasswordException(String message) {
        super(message);
    }
}