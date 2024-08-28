package br.ufal.ic.p2.myfood.exceptions;

public class OutofBoundsException extends Exception {
    public OutofBoundsException() {
        super("Indice maior que o esperado");
    }

    public OutofBoundsException(String message) {
        super(message);
    }
}