package br.ufal.ic.p2.myfood.exceptions;

public class RepeatCompanyException extends Exception {
    public RepeatCompanyException() {
        super("Empresa com esse nome ja existe");
    }

    public RepeatCompanyException(String message) {
        super(message);
    }
}