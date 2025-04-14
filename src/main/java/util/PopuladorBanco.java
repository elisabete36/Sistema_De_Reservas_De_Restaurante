package util;

import jakarta.persistence.EntityManager;
import model.Cliente;
import model.FormaPagamento;
import model.Mesa;
import model.Reserva;

import java.util.Date;

public class PopuladorBanco {

    public static void popular(EntityManager em) {
        // Inicia a transação
        em.getTransaction().begin();

        // Criação de Formas de Pagamento
        FormaPagamento formaPagamentoDinheiro = new FormaPagamento("Dinheiro");
        FormaPagamento formaPagamentoCartao = new FormaPagamento("Cartão");

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
        Mesa mesa1 = new Mesa(1, true); // Mesa 1, VIP
        Mesa mesa2 = new Mesa(2, false); // Mesa 2, não VIP

        // Persistindo as mesas
        em.persist(mesa1);
        em.persist(mesa2);

        // Criação de Reservas
        Reserva reserva1 = new Reserva(cliente1, mesa1, new Date(), 2, true, formaPagamentoDinheiro, new BigDecimal("150.00"));
        Reserva reserva2 = new Reserva(cliente2, mesa2, new Date(), 4, false, formaPagamentoCartao, new BigDecimal("200.00"));

        // Persistindo as reservas
        em.persist(reserva1);
        em.persist(reserva2);

        // Commit da transação
        em.getTransaction().commit();
    }
}
