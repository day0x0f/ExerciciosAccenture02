package Atividade10;

import java.util.List;

public class SimuladorEcommerce {
    public static void main(String[] args) {
        OrderProcessor sistema = new OrderProcessor();

        // 1. Cadastro
        sistema.cadastrarProduto(new Product("SKU01", "Cadeira Gamer", "Moveis", 1200.0, new int[]{5}));
        sistema.cadastrarProduto(new Product("SKU02", "Monitor 4K", "Tech", 3500.0, new int[]{3}));
        sistema.cadastrarCliente(new Client("C01", "Tony Stark"));

        // 2. Criar Pedido
        Order pedido1 = new Order("C01", List.of(
                new OrderItem("SKU01", 1),
                new OrderItem("SKU02", 1)
        ));
        sistema.getPedidos().add(pedido1);

        // 3. Fluxo de Processamento
        System.out.println("Iniciando processamento do pedido: " + pedido1.id);
        sistema.atualizarStatus(pedido1.id, OrderStatus.RESERVED);
        sistema.atualizarStatus(pedido1.id, OrderStatus.PAID);

        // 4. Listagem e Relatórios
        System.out.println("\nProdutos disponíveis (ordenados por preço):");
        sistema.listarProdutos(true);

        sistema.gerarRelatorio();
    }
}