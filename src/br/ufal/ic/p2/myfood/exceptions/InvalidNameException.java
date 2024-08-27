package br.ufal.ic.p2.myfood.exceptions;

public class InvalidNameException extends Exception{
    public InvalidNameException() {
        super("Nome invalido");
    }
    public InvalidNameException(String message) {
        super(message);
    }
}