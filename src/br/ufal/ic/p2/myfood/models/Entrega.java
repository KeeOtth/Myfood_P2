package br.ufal.ic.p2.myfood.models;

public class Entrega {
    private static int contador = 1; //Para gerar o id único dos Usuários
    private int id;
    private Pedido pedido;
    private String destino;
    private Entregador entregador;

    // O construtor vazio é necessário para a serialização e desserialização
    public Entrega(){
    }

    public Entrega(Pedido pedido, Entregador entregador, String destino) {
        this.id = contador++;
        this.pedido = pedido;
        this.entregador = entregador;
        if (destino == null) {
            this.destino = pedido.getCliente().getEndereco();
        } else {
            this.destino = destino;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public String getAtributo(String atributo) {
        return switch (atributo) {
            case "cliente" -> pedido.getCliente().getNome();
            case "empresa" -> pedido.getEmpresa().getNome();
            case "pedido" -> String.valueOf(pedido.getId()) ;
            case "entregador" -> entregador.getNome();
            case "destino" -> destino;
            case "produtos" -> "{" + String.valueOf(pedido.getProd_list()) + "}";
            default -> throw new IllegalArgumentException("Atributo nao existe");
        };
    }

    @Override
    public String toString() {
        return "Entrega{" +
                "empresa=" + pedido.getEmpresa().getNome() +
                ", destino='" + destino + '\'' +
                ", entregador=" + entregador +
                '}';
    }
}
