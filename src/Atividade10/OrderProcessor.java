package Atividade10;

import java.util.*;
import java.util.stream.Collectors;

public class OrderProcessor {
    private final Map<String, Product> produtos = new HashMap<>();
    private final Map<String, Client> clientes = new HashMap<>();
    private final List<Order> pedidos = new ArrayList<>();

    public void cadastrarProduto(Product p) { produtos.put(p.sku(), p); }
    public void cadastrarCliente(Client c) { clientes.put(c.id(), c); }

    public void listarProdutos(boolean porPreco) {
        produtos.values().stream()
                .sorted(porPreco ? Comparator.comparing(Product::preco) : Comparator.comparing(Product::sku))
                .forEach(p -> System.out.printf("[%s] %s | R$%.2f | Est: %d%n", p.sku(), p.nome(), p.preco(), p.qtd()));
    }

    public void atualizarStatus(String orderId, OrderStatus novoStatus) {
        Order pedido = pedidos.stream()
                .filter(o -> o.id.equals(orderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Pattern Matching for Switch - Lógica de transição de estados
        switch (novoStatus) {
            case RESERVED -> {
                if (validarEstoque(pedido)) {
                    alterarEstoqueFisico(pedido, -1); // Reserva (subtrai)
                    pedido.status = OrderStatus.RESERVED;
                } else {
                    System.out.println("Erro: Estoque insuficiente para o pedido " + orderId);
                }
            }
            case PAID -> pedido.status = OrderStatus.PAID;
            case FAILED, CANCELLED -> {
                // Se estava reservado, devolvemos os itens ao estoque
                if (pedido.status == OrderStatus.RESERVED) {
                    alterarEstoqueFisico(pedido, 1); // Libera (soma)
                }
                pedido.status = novoStatus;
            }
            default -> pedido.status = novoStatus;
        }
    }

    private boolean validarEstoque(Order o) {
        return o.itens.stream().allMatch(i -> produtos.get(i.sku()).qtd() >= i.quantidade());
    }

    private void alterarEstoqueFisico(Order o, int sinal) {
        o.itens.forEach(i -> produtos.get(i.sku()).ajustarEstoque(i.quantidade() * sinal));
    }

    public void gerarRelatorio() {
        System.out.println("\n--- RELATÓRIO DE VENDAS (PAID) ---");

        double faturamento = pedidos.stream()
                .filter(p -> p.status == OrderStatus.PAID)
                .flatMap(p -> p.itens.stream())
                .mapToDouble(i -> i.quantidade() * produtos.get(i.sku()).preco())
                .sum();

        System.out.printf("Faturamento Total: R$%.2f%n", faturamento);

        System.out.println("Top 3 Produtos mais vendidos:");
        pedidos.stream()
                .filter(p -> p.status == OrderStatus.PAID)
                .flatMap(p -> p.itens.stream())
                .collect(Collectors.groupingBy(OrderItem::sku, Collectors.summingInt(OrderItem::quantidade)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(e -> System.out.println(" - " + produtos.get(e.getKey()).nome() + ": " + e.getValue() + " un"));
    }

    public List<Order> getPedidos() { return pedidos; }
}