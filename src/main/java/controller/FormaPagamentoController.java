package controller;

import model.FormaPagamento;
import service.FormaPagamentoService;

import java.util.List;
import java.util.Scanner;

public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;
    private final Scanner scanner;

    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
        this.scanner = new Scanner(System.in);
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n--- Menu Forma de Pagamento ---");
            System.out.println("1. Cadastrar nova forma de pagamento");
            System.out.println("2. Listar todas as formas de pagamento");
            System.out.println("3. Buscar por descrição");
            System.out.println("4. Calcular troco");
            System.out.println("5. Validar forma de pagamento por ID");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listarTodas();
                case 3 -> buscarPorDescricao();
                case 4 -> calcularTroco();
                case 5 -> validarFormaPagamento();
                case 0 -> System.out.println("Saindo do menu de formas de pagamento...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void cadastrar() {
        System.out.print("Digite a descrição da forma de pagamento: ");
        String descricao = scanner.nextLine();

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setDescricao(descricao);

        FormaPagamento cadastrada = formaPagamentoService.cadastrar(formaPagamento);
        System.out.println("Forma cadastrada com ID: " + cadastrada.getId());
    }

    public void listarTodas() {
        List<FormaPagamento> lista = formaPagamentoService.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma forma de pagamento cadastrada.");
        } else {
            lista.forEach(fp -> System.out.println(fp.getId() + ": " + fp.getDescricao()));
        }
    }

    public void buscarPorDescricao() {
        System.out.print("Digite a descrição para buscar: ");
        String descricao = scanner.nextLine();

        List<FormaPagamento> resultados = formaPagamentoService.buscarPorDescricao(descricao);
        resultados.forEach(fp -> System.out.println(fp.getId() + ": " + fp.getDescricao()));
    }

    public void calcularTroco() {
        try {
            System.out.print("Digite o valor total da compra: ");
            double total = scanner.nextDouble();

            System.out.print("Digite o valor pago: ");
            double pago = scanner.nextDouble();

            double troco = formaPagamentoService.calcularTroco(total, pago);
            System.out.printf("Troco: R$ %.2f\n", troco);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: Valor pago é menor que o valor total.");
        }
    }

    public void validarFormaPagamento() {
        System.out.print("Digite o ID da forma de pagamento: ");
        long id = scanner.nextLong();

        boolean valida = formaPagamentoService.validarFormaPagamento(id);
        System.out.println(valida ? "Forma de pagamento válida." : "Forma de pagamento inválida.");
    }
}
