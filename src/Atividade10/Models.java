package Atividade10;

import java.util.UUID;
import java.util.List;

// O uso de int[] para estoque permite mutabilidade controlada dentro de um Record
record Product(String sku, String nome, String categoria, double preco, int[] estoque) {
    public int qtd() { return estoque[0]; }
    public void ajustarEstoque(int delta) { estoque[0] += delta; }
}

record Client(String id, String nome) {}

record OrderItem(String sku, int quantidade) {}

class Order {
    final String id = UUID.randomUUID().toString().substring(0, 5);
    final String clientId;
    final List<OrderItem> itens;
    OrderStatus status = OrderStatus.PENDING;

    Order(String clientId, List<OrderItem> itens) {
        this.clientId = clientId;
        this.itens = List.copyOf(itens);
    }
}