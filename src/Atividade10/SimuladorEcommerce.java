package Atividade10;

import java.util.List;

public class SimuladorEcommerce {
    public static void main(String[] args) {
        OrderProcessor processor = new OrderProcessor();

        // 1. Adicionar Produtos
        processor.addProduto(new Product("A1", "Notebook", "Tech", 4000.0, new int[]{5}));
        processor.addProduto(new Product("B2", "Mouse", "Tech", 150.0, new int[]{20}));
        processor.addProduto(new Product("C3", "Cadeira", "Office", 1200.0, new int[]{10}));

        // 2. Adicionar Cliente
        processor.addCliente(new Client("USER01", "Luke Skywalker"));

        // 3. Listar Produtos Ordenados por Preço
        System.out.println("Catálogo de Produtos (Por Preço):");
        processor.listarProdutos(true);

        // 4. Fluxo de Pedido
        System.out.println("\nIniciando Pedido...");
        String idPedido = processor.criarPedido("USER01", List.of(
                new OrderItem("A1", 1),
                new OrderItem("B2", 2)
        ));

        processor.reservarEstoque(idPedido);
        processor.pagarPedido(idPedido, true); // Simula pagamento aprovado

        // 5. Gerar Relatórios
        processor.gerarRelatorios();
    }
}