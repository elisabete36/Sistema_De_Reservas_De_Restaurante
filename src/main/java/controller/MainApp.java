package com.restaurante.controller;

import java.util.Date;
import model.*;
import repository.*;
import util.PopularBanco;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainApp {
    public static void main(String[] args) {
        try {
            // Configura o Hibernate
            SessionFactory sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();

            // Popula o banco de dados com dados iniciais
            PopularBanco.popular();

            // Testa as consultas
            testarConsultas();

            System.out.println("Aplicação executada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro na aplicação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testarConsultas() {
        System.out.println("\n=== TESTANDO CONSULTAS ===");

        ClienteRepository clienteRepo = new ClienteRepository();
        ReservaRepository reservaRepo = new ReservaRepository();
        FormaPagamentoRepository formaPagamentoRepo = new FormaPagamentoRepository();

        try {
            // Testa consulta com LIKE
            System.out.println("\nClientes com nome similar a 'João':");
            clienteRepo.findByNomeSimilar("João").forEach(System.out::println);

            // Testa consulta com JOIN
            System.out.println("\nReservas com informações de clientes:");
            reservaRepo.findWithClientes().forEach(System.out::println);

            // Testa consulta com intervalo de datas
            Date hoje = new Date();
            Date amanha = new Date(hoje.getTime() + (1000 * 60 * 60 * 24));
            System.out.println("\nReservas entre hoje e amanhã:");
            reservaRepo.findByDataBetween(hoje, amanha).forEach(System.out::println);

            // Testa consulta de agregação
            System.out.println("\nMédia de reservas por dia:");
            System.out.println(reservaRepo.getMediaReservasPorDia());

            // Testa novas funcionalidades de pagamento
            System.out.println("\nFormas de pagamento ativas:");
            formaPagamentoRepo.listarAtivas().forEach(System.out::println);

            System.out.println("\nReservas pagas:");
            reservaRepo.findByStatusPagamento(true).forEach(System.out::println);

            System.out.println("\nReservas não pagas:");
            reservaRepo.findByStatusPagamento(false).forEach(System.out::println);

            // Testa atualização de pagamento
            if (!reservaRepo.findByStatusPagamento(false).isEmpty()) {
                Long reservaId = reservaRepo.findByStatusPagamento(false).get(0).getId();
                System.out.println("\nRegistrando pagamento para reserva ID: " + reservaId);
                reservaRepo.atualizarPagamento(reservaId, true, 1L); // Assumindo que ID 1 é uma forma de pagamento válida
                System.out.println("Pagamento registrado com sucesso!");
            }

            // Testa cálculo de receitas
            System.out.println("\nTotal de receitas:");
            System.out.println("R$ " + reservaRepo.getTotalReceitas(hoje, amanha));
        } finally {
            clienteRepo.close();
            reservaRepo.close();
            formaPagamentoRepo.close();
        }
    }
}