package com.example.teste;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    String nome;
    LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getFuncao() {
        return null;
    }
}

class Funcionario extends Pessoa {
    BigDecimal salario;
    String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

}

public class Principal {

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // Inserir os funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009,44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284,38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836,14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119,88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234,68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582,72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071,84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017,45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606,85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("9836,14"), "Gerente"));

        // Remover o funcionário "João"
        funcionarios.removeIf(funcionario -> funcionario.nome.equals("João"));

        // Formatar e imprimir todas as informações dos funcionários
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Informações dos Funcionários:");
        funcionarios.forEach(funcionario -> System.out.println("Nome: " + funcionario.nome +
                " | Data de Nascimento: " + funcionario.dataNascimento.format(formatter) +
                " | Salário: " + String.format("%,.2f", funcionario.salario)));

        // Aumento de 10% no salário dos funcionários
        funcionarios.forEach(funcionario -> funcionario.salario = funcionario.salario.multiply(BigDecimal.valueOf(1.1)));

        // Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimir os funcionários agrupados por função
        System.out.println("\nFuncionários Agrupados por Função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println(" - Nome: " + f.nome));
        });

        // Imprimir os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\nFuncionários com Aniversário em Outubro e Dezembro:");
        funcionarios.stream()
                .filter(funcionario -> funcionario.dataNascimento.getMonthValue() == 10 || funcionario.dataNascimento.getMonthValue() == 12)
                .forEach(funcionario -> System.out.println("Nome: " + funcionario.nome));

        // Imprimir o funcionário com a maior idade
        System.out.println("\nFuncionário mais Velho:");
        Funcionario maisVelho = Collections.max(funcionarios, Comparator.comparing(f -> f.dataNascimento));
        int idadeMaisVelho = LocalDate.now().getYear() - maisVelho.dataNascimento.getYear();
        System.out.println("Nome: " + maisVelho.nome + " | Idade: " + idadeMaisVelho);

        // Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\nLista de Funcionários em Ordem Alfabética:");
        funcionarios.stream()
                .map(funcionario -> funcionario.nome)
                .sorted()
                .forEach(System.out::println);

        // Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos Salários dos Funcionários: " + String.format("%,.2f", totalSalarios));

        // Imprimir quantos salários mínimos cada funcionário ganha (considerando o salário mínimo de R$1212.00)
        System.out.println("\nSalários Mínimos Recebidos por Cada Funcionário:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.salario.divide(salarioMinimo, 2, RoundingMode.DOWN);
            System.out.println("Funcionário " + funcionario.nome + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }
}


