package Atividade10;

import java.util.List;
import java.util.UUID;

// Produto com SKU, nome, categoria, preço e estoque (vetor para permitir alteração interna)
record Product(String sku, String nome, String categoria, double preco, int[] estoque) {
    public int getQtd() { return estoque[0]; }
    public void setQtd(int valor) { estoque[0] = valor; }
}

record Client(String id, String nome) {}

record OrderItem(String sku, int quantidade) {}

class Order {
    final String id;
    final String clientId;
    final List<OrderItem> itens;
    OrderStatus status = OrderStatus.PENDING;

    Order(String clientId, List<OrderItem> itens) {
        this.id = UUID.randomUUID().toString().substring(0, 5);
        this.clientId = clientId;
        this.itens = List.copyOf(itens);
    }
}