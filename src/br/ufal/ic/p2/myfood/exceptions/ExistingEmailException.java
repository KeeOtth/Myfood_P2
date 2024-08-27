package br.ufal.ic.p2.myfood.exceptions;

public class ExistingEmailException extends Exception{
    public ExistingEmailException() {
        super("Conta com esse email ja existe");
    }
    public ExistingEmailException(String message) {
        super(message);
    }
}