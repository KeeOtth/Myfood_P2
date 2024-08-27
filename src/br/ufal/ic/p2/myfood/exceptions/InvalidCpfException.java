package br.ufal.ic.p2.myfood.exceptions;

public class InvalidCpfException extends Exception{
    public InvalidCpfException() {
        super("CPF invalido");
    }
    public InvalidCpfException(String message) {
        super(message);
    }
}