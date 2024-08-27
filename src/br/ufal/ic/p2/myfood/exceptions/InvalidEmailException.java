package br.ufal.ic.p2.myfood.exceptions;

public class InvalidEmailException extends Exception{
    public InvalidEmailException() {
        super("Email invalido");
    }
    public InvalidEmailException(String message) {
        super(message);
    }
}
