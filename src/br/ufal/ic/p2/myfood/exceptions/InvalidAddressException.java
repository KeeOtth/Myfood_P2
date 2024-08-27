package br.ufal.ic.p2.myfood.exceptions;

public class InvalidAddressException extends Exception{
    public InvalidAddressException() {
        super("Endereco invalido");
    }
    public InvalidAddressException(String message) {
        super(message);
    }
}