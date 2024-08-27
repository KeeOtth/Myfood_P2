package br.ufal.ic.p2.myfood.exceptions;

public class InvalidLoginOrPasswordException extends Exception{
    public InvalidLoginOrPasswordException() {
        super("Login ou senha invalidos");
    }
    public InvalidLoginOrPasswordException(String message) {
        super(message);
    }
}