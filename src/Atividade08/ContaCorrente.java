package Atividade08;

import java.time.LocalDate;

public class ContaCorrente {
    private final int numero;
    private double saldo;
    private final LocalDate data;
    private final Cliente cliente;

    public ContaCorrente(int numero, double saldoInicial, Cliente cliente) {
        this.numero = numero;
        this.saldo = saldoInicial;
        this.cliente = cliente;
        this.data = LocalDate.now();
    }

    public void depositar(double valor) {
        this.saldo += valor;
        System.out.println("Depósito de R$" + valor + " realizado. Saldo atual: R$" + this.saldo);
    }

    public void sacar(double valor) {
        this.saldo -= valor;
        System.out.println("Saque de R$" + valor + " realizado. Saldo atual: R$" + this.saldo);
    }

    public void transferir(ContaCorrente destino, double valor) {
        if (this.saldo - valor < 0) {
            System.out.println("Transferência cancelada: Saldo insuficiente!");
            return;
        }
        this.sacar(valor);
        destino.depositar(valor);
    }

    public void exibirExtrato() {
        System.out.println("--- Extrato ---");
        System.out.println("Conta: " + numero + " | Data: " + data);
        System.out.println("Cliente: " + cliente.getNomeCompleto());
        System.out.println("Saldo: R$" + saldo);
    }

    public Cliente getCliente() { return cliente; }
}