package Atividade08;

public class Cliente {
    private final String nome;
    private final String sobrenome;
    private String cpf;

    public Cliente(String nome, String sobrenome, String cpf) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }
}