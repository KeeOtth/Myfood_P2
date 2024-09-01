package br.ufal.ic.p2.myfood.exceptions;

public class OrderCreationException extends Exception {
    public OrderCreationException(){
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }
    public OrderCreationException(String message){
        super(message);
    }
}
