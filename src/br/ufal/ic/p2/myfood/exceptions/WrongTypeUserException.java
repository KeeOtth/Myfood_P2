package br.ufal.ic.p2.myfood.exceptions;

public class WrongTypeUserException extends Exception{
    public WrongTypeUserException() {
        super("Usuario nao pode criar uma empresa");
    }
    public WrongTypeUserException(String message) {
        super(message);
    }
}