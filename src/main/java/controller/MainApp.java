package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Cliente;
import model.FormaPagamento;
import model.Mesa;
import model.Reserva;
import repository.ClienteRepository;
import repository.ReservaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class MainApp {

    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("reservaPU");
        EntityManager em = emf.createEntityManager();

        ReservaRepository reservaRepo = new ReservaRepository(em);
        ClienteRepository clienteRepo = new ClienteRepository(em);

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            logger.info("\n--- MENU PRINCIPAL ---\n" +
                    "1. Cadastrar Restaurante\n" +
                    "2. Cadastrar Usuário\n" +
                    "3. Cadastrar Funcionário\n" +
                    "4. Criar Reserva\n" +
                    "5. Cancelar Reserva\n" +
                    "6. Registrar Pagamento\n" +
                    "7. Listar Reservas Canceladas\n" +
                    "0. Sair");

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    logger.info("Funcionalidade de cadastro de restaurante...");
                    break;
                case 2:
                    logger.info("Funcionalidade de cadastro de usuário...");
                    break;
                case 3:
                    logger.info("Funcionalidade de cadastro de funcionário...");
                    break;
                case 4:
                    logger.info("Criando uma nova reserva...");

                    System.out.print("Nome do cliente: ");
                    String nomeCliente = scanner.nextLine();

                    Cliente cliente = new Cliente();
                    cliente.setNome(nomeCliente);
                    clienteRepo.salvar(cliente);

                    System.out.print("Número da mesa: ");
                    int numeroMesa = scanner.nextInt();
                    scanner.nextLine();

                    Mesa mesa = new Mesa();
                    mesa.setNumero(numeroMesa);

                    System.out.print("Data da reserva (format YYYY-MM-DD): ");
                    String dataStr = scanner.nextLine();
                    Date dataReserva = Date.valueOf(dataStr);

                    System.out.print("Forma de pagamento (DINHEIRO ou CARTAO): ");
                    String formaStr = scanner.nextLine().toUpperCase();
                    FormaPagamento formaPagamento = FormaPagamento.valueOf(formaStr);

                    System.out.print("Valor da reserva: ");
                    double valor = scanner.nextDouble();
                    scanner.nextLine();

                    Reserva reserva = new Reserva(cliente, mesa, dataReserva, formaPagamento, valor);
                    reservaRepo.salvar(reserva);

                    logger.info("Reserva criada com sucesso!");
                    break;

                case 5:
                    System.out.print("ID da reserva a cancelar: ");
                    Long idCancelar = scanner.nextLong();
                    reservaRepo.cancelarReserva(idCancelar);
                    logger.info("Reserva cancelada com sucesso.");
                    break;

                case 6:
                    System.out.print("ID da reserva para registrar pagamento: ");
                    Long idPagar = scanner.nextLong();

                    System.out.print("Valor do pagamento: ");
                    double valorPagamento = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Forma de pagamento (DINHEIRO ou CARTAO): ");
                    String formaStrPagar = scanner.nextLine().toUpperCase();
                    FormaPagamento formaPagar = FormaPagamento.valueOf(formaStrPagar);

                    reservaRepo.registrarPagamento(idPagar, valorPagamento, formaPagar);
                    logger.info("Pagamento registrado com sucesso.");
                    break;

                case 7:
                    List<Reserva> canceladas = reservaRepo.listarReservasCanceladas();
                    logger.info("--- Reservas Canceladas ---");
                    for (Reserva r : canceladas) {
                        logger.info("ID: " + r.getId() + " | Cliente: " + r.getCliente().getNome());
                    }
                    break;

                case 0:
                    logger.info("Encerrando o sistema.");
                    break;

                default:
                    logger.warning("Opção inválida.");
            }

        } while (opcao != 0);

        em.close();
        emf.close();
        scanner.close();
    }
}
