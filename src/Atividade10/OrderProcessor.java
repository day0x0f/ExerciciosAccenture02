package Atividade10;

import java.util.*;
import java.util.stream.Collectors;

public class OrderProcessor {
    private final Map<String, Product> produtos = new HashMap<>();
    private final Map<String, Client> clientes = new HashMap<>();
    private final List<Order> pedidos = new ArrayList<>();

    public void addProduto(Product p) { produtos.put(p.sku(), p); }
    public void addCliente(Client c) { clientes.put(c.id(), c); }

    public void listarProdutos(boolean porPreco) {
        produtos.values().stream()
                .sorted(porPreco ? Comparator.comparing(Product::preco) : Comparator.comparing(Product::sku))
                .forEach(p -> System.out.printf("SKU: %s | %s | R$%.2f | Estoque: %d%n",
                        p.sku(), p.nome(), p.preco(), p.getQtd()));
    }

    public String criarPedido(String clientId, List<OrderItem> itens) {
        Order novo = new Order(clientId, itens);
        pedidos.add(novo);
        return novo.id;
    }

    public void reservarEstoque(String orderId) {
        Order pedido = buscarPedido(orderId);
        boolean disponivel = pedido.itens.stream()
                .allMatch(i -> produtos.get(i.sku()).getQtd() >= i.quantidade());

        if (disponivel) {
            pedido.itens.forEach(i -> {
                Product p = produtos.get(i.sku());
                p.setQtd(p.getQtd() - i.quantidade());
            });
            pedido.status = OrderStatus.RESERVED;
        }
    }

    public void pagarPedido(String orderId, boolean sucesso) {
        Order pedido = buscarPedido(orderId);
        if (sucesso) {
            pedido.status = OrderStatus.PAID;
        } else {
            pedido.status = OrderStatus.FAILED;
            cancelarPedido(orderId); // Libera estoque automaticamente se falhar
        }
    }

    public void cancelarPedido(String orderId) {
        Order pedido = buscarPedido(orderId);
        if (pedido.status == OrderStatus.RESERVED || pedido.status == OrderStatus.FAILED) {
            pedido.itens.forEach(i -> {
                Product p = produtos.get(i.sku());
                p.setQtd(p.getQtd() + i.quantidade());
            });
            pedido.status = OrderStatus.CANCELLED;
        }
    }

    private Order buscarPedido(String id) {
        return pedidos.stream().filter(o -> o.id.equals(id)).findFirst().orElseThrow();
    }

    public void gerarRelatorios() {
        System.out.println("\n--- RELATÓRIOS DO SISTEMA ---");

        double faturamento = pedidos.stream()
                .filter(o -> o.status == OrderStatus.PAID)
                .flatMap(o -> o.itens.stream())
                .mapToDouble(i -> i.quantidade() * produtos.get(i.sku()).preco())
                .sum();

        var top3 = pedidos.stream()
                .filter(o -> o.status == OrderStatus.PAID)
                .flatMap(o -> o.itens.stream())
                .collect(Collectors.groupingBy(OrderItem::sku, Collectors.summingInt(OrderItem::quantidade)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .toList();

        var fatPorCat = pedidos.stream()
                .filter(o -> o.status == OrderStatus.PAID)
                .flatMap(o -> o.itens.stream())
                .collect(Collectors.groupingBy(i -> produtos.get(i.sku()).categoria(),
                        Collectors.summingDouble(i -> i.quantidade() * produtos.get(i.sku()).preco())));

        System.out.printf("Faturamento Total: R$ %.2f%n", faturamento);
        System.out.println("Top 3 SKU/Qtd: " + top3);
        System.out.println("Faturamento por Categoria: " + fatPorCat);
    }
}