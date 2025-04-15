package util;

import jakarta.persistence.EntityManager;
import model.Cliente;
import model.FormaPagamento;
import model.FormaPagamento.Tipo;
import model.Mesa;
import model.Reserva;
import model.Status;

import java.math.BigDecimal;
import java.util.Date;

public class PopuladorBanco {

    public static void popular(EntityManager em) {
        // Inicia a transação
        em.getTransaction().begin();

        // Criação de Formas de Pagamento
        FormaPagamento formaPagamentoDinheiro = new FormaPagamento(Tipo.DINHEIRO, "Pagamento em dinheiro");
        FormaPagamento formaPagamentoCartao = new FormaPagamento(Tipo.CARTAO_CREDITO, "Cartão de crédito");

        // Persistindo as formas de pagamento
        em.persist(formaPagamentoDinheiro);
        em.persist(formaPagamentoCartao);

        // Criação de Clientes
        Cliente cliente1 = new Cliente("João", "joao@example.com");
        Cliente cliente2 = new Cliente("Maria", "maria@example.com");

        // Persistindo os clientes
        em.persist(cliente1);
        em.persist(cliente2);

        // Criação de Mesas
        Mesa mesa1 = new Mesa("M01", 4, true);   // Mesa VIP
        Mesa mesa2 = new Mesa("M02", 6, false);  // Mesa normal

        // Persistindo as mesas
        em.persist(mesa1);
        em.persist(mesa2);

        // Criação de Reservas
        Reserva reserva1 = new Reserva(
                cliente1,
                mesa1,
                new Date(),
                2,
                true,
                formaPagamentoDinheiro,
                new BigDecimal("150.00"),
                Status.ATIVA
        );

        Reserva reserva2 = new Reserva(
                cliente2,
                mesa2,
                new Date(),
                4,
                false,
                formaPagamentoCartao,
                new BigDecimal("200.00"),
                Status.CONCLUIDA
        );

        // Persistindo as reservas
        em.persist(reserva1);
        em.persist(reserva2);

        // Commit da transação
        em.getTransaction().commit();
    }
}
