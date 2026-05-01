package Atividade09;

import java.util.Scanner;
import java.util.Arrays;

public class SistemaEscolar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalAlunos = 5;

        String[] nomes = new String[totalAlunos];
        double[][] notas = new double[totalAlunos][3];
        double[] medias = new double[totalAlunos];

        System.out.println("--- Cadastro de Alunos ---");

        for (int i = 0; i < totalAlunos; i++) {
            nomes[i] = lerNome(scanner, i + 1);
            notas[i][0] = lerNota(scanner, "Nota 1: ");
            notas[i][1] = lerNota(scanner, "Nota 2: ");
            notas[i][2] = lerNota(scanner, "Nota 3: ");
            medias[i] = (notas[i][0] + notas[i][1] + notas[i][2]) / 3.0;
        }

        // --- 1. Relatório Individual ---
        System.out.println("\n1. Relatório Individual:");
        for (int i = 0; i < totalAlunos; i++) {
            String status = medias[i] >= 70 ? "APROVADO" : (medias[i] >= 50 ? "RECUPERAÇÃO" : "REPROVADO");
            System.out.printf("%s | Notas: %.1f, %.1f, %.1f | Média: %.2f | %s%n",
                    nomes[i], notas[i][0], notas[i][1], notas[i][2], medias[i], status);
        }

        // --- 2. Estatísticas da Turma ---
        double maiorMedia = Arrays.stream(medias).max().orElse(0);
        double menorMedia = Arrays.stream(medias).min().orElse(0);
        double mediaGeral = Arrays.stream(medias).average().orElse(0);

        System.out.println("\n2. Estatísticas da Turma:");
        System.out.printf("Maior média: %.2f%n", maiorMedia);
        System.out.printf("Menor média: %.2f%n", menorMedia);
        System.out.printf("Média geral da turma: %.2f%n", mediaGeral);

        // --- 3. Distribuição de Resultados ---
        long aprovados = Arrays.stream(medias).filter(m -> m >= 70).count();
        long recuperacao = Arrays.stream(medias).filter(m -> m >= 50 && m < 70).count();
        long reprovados = Arrays.stream(medias).filter(m -> m < 50).count();

        System.out.println("\n3. Distribuição de Resultados:");
        System.out.println("APROVADOS: " + aprovados);
        System.out.println("RECUPERAÇÃO: " + recuperacao);
        System.out.println("REPROVADOS: " + reprovados);

        // --- 4. Melhor(es) Aluno(s) ---
        System.out.println("\n4. Melhor(es) Aluno(s):");
        for (int i = 0; i < totalAlunos; i++) {
            String msg = (medias[i] == maiorMedia) ? nomes[i] + " (Média: " + maiorMedia + ")" : "";
            boolean skip = msg.isEmpty();
            switch (Boolean.toString(skip)) {
                case "false" -> System.out.println(msg);
            }
        }
    }

    private static String lerNome(Scanner sc, int index) {
        System.out.print("Nome do aluno " + index + ": ");
        String n = sc.next();
        try {
            boolean invalido = n.length() < 3;
            switch (Boolean.toString(invalido)) {
                case "true" -> throw new Exception();
            }
            return n;
        } catch (Exception e) {
            System.out.println("Erro: Nome deve ter no mínimo 3 caracteres!");
            return lerNome(sc, index);
        }
    }

    private static double lerNota(Scanner sc, String label) {
        System.out.print(label);
        try {
            double nota = Double.parseDouble(sc.next());
            boolean foraEscopo = (nota < 0 || nota > 100);
            return switch (Boolean.toString(foraEscopo)) {
                case "true" -> throw new Exception();
                default -> nota;
            };
        } catch (Exception e) {
            System.out.println("Erro: Nota inválida! Digite um valor entre 0 e 100.");
            return lerNota(sc, label);
        }
    }
}