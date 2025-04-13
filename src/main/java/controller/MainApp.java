package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repository.ClienteRepository;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("reservaPU");
        EntityManager em = emf.createEntityManager();

        ClienteRepository clienteRepo = new ClienteRepository(em);
        ReservaRepository reservaRepo = new ReservaRepository(em);

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar Restaurante");
            System.out.println("2. Cadastrar Usuário");
            System.out.println("3. Cadastrar Funcionário");
            System.out.println("4. Criar Reserva");
            System.out.println("5. Cancelar Reserva");
            System.out.println("6. Registrar Pagamento");
            System.out.println("7. Listar Reservas Canceladas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.println("Funcionalidade de cadastro de restaurante...");
                    break;
                case 2:
                    System.out.println("Funcionalidade de cadastro de usuário...");
                    break;
                case 3:
                    System.out.println("Funcionalidade de cadastro de funcionário...");
                    break;
                case 4:
                    System.out.println("Funcionalidade de criação de reserva...");
                    break;
                case 5:
                    System.out.print("ID da reserva a cancelar: ");
                    Long idCancelar = scanner.nextLong();
                    reservaRepo.cancelarReserva(idCancelar);
                    System.out.println("Reserva cancelada com sucesso.");
                    break;
                case 6:
                    System.out.print("ID da reserva para registrar pagamento: ");
                    Long idPagar = scanner.nextLong();
                    reservaRepo.registrarPagamento(idPagar);
                    System.out.println("Pagamento registrado com sucesso.");
                    break;
                case 7:
                    List<Reserva> canceladas = reservaRepo.listarReservasCanceladas();
                    System.out.println("--- Reservas Canceladas ---");
                    for (Reserva r : canceladas) {
                        System.out.println("ID: " + r.getId() + " | Cliente: " + r.getCliente().getNome());
                    }
                    break;
                case 0:
                    System.out.println("Encerrando o sistema.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        em.close();
        emf.close();
        scanner.close();
    }
}
