package Atividade08;

public class PrincipalContaCorrente {
    public static void main(String[] args) {
        // Instanciando clientes
        Cliente cli1 = new Cliente("João", "Silva", "123.456.789-00");
        Cliente cli2 = new Cliente("Maria", "Souza", "987.654.321-11");

        // Instanciando contas
        ContaCorrente conta1 = new ContaCorrente(101, 500.0, cli1);
        ContaCorrente conta2 = new ContaCorrente(102, 100.0, cli2);

        // Executando operações
        System.out.println("Bem-vindo, " + conta1.getCliente().getNomeCompleto());

        conta1.depositar(200.0);
        conta1.sacar(100.0);

        // Teste de transferência (Cancelamento se ficar negativo)
        conta1.transferir(conta2, 1000.0);

        // Transferência válida
        conta1.transferir(conta2, 200.0);

        conta1.exibirExtrato();
        conta2.exibirExtrato();
    }
}