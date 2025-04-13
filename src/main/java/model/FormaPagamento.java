package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Formapagamento {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.print("Digite o ID da reserva: ");
        int idReserva = scanner.nextInt();


        System.out.print("Digite o valor do pagamento: ");
        double valor = scanner.nextDouble();


        System.out.println("Escolha a forma de pagamento:");
        System.out.println("1 - Dinheiro");
        System.out.println("2 - Cartão de Crédito");
        System.out.println("3 - Cartão de Débito");
        System.out.print("Digite a opção (1, 2 ou 3): ");
        int escolha = scanner.nextInt();


        FormaPagamento formaPagamento;
        switch (escolha) {
            case 1:
                formaPagamento = FormaPagamento.DINHEIRO;
                break;
            case 2:
                formaPagamento = FormaPagamento.CARTAO_CREDITO;
                break;
            case 3:
                formaPagamento = FormaPagamento.CARTAO_DEBITO;
                break;
            default:
                formaPagamento = null;
                System.out.println("Opção inválida.");
                scanner.close();
                return;
        }


        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/seubanco", "usuario", "senha");

            String sql = "INSERT INTO pagamentos (reserva_id, valor, forma_pagamento) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idReserva);
            stmt.setDouble(2, valor);
            stmt.setString(3, formaPagamento.name());  // Usando o nome do enum para salvar (ex: "DINHEIRO")

            stmt.executeUpdate();
            stmt.close();
            con.close();

            System.out.println("Pagamento registrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar pagamento: " + e.getMessage());
        }

        scanner.close();
    }
}
abstract