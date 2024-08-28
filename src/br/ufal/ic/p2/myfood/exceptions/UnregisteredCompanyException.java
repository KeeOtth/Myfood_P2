package br.ufal.ic.p2.myfood.exceptions;

public class UnregisteredCompanyException extends Exception {
    public UnregisteredCompanyException() {
        super("Empresa nao cadastrada");
    }

    public UnregisteredCompanyException(String message) {
        super(message);
    }
}