package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
        em.getTransaction().begin();

        // Verificar e criar formas de pagamento
        if (!existeFormaPagamento(em, Tipo.DINHEIRO)) {
            em.persist(new FormaPagamento(Tipo.DINHEIRO, "Pagamento em dinheiro"));
        }

        if (!existeFormaPagamento(em, Tipo.CARTAO_CREDITO)) {
            em.persist(new FormaPagamento(Tipo.CARTAO_CREDITO, "Cartão de crédito"));
        }

        // Verificar e criar clientes
        Cliente cliente1 = buscarOuCriarCliente(em, "João", "joao@example.com");
        Cliente cliente2 = buscarOuCriarCliente(em, "Maria", "maria@example.com");

        // Verificar e criar mesas
        Mesa mesa1 = buscarOuCriarMesa(em, "M01", 4, true);
        Mesa mesa2 = buscarOuCriarMesa(em, "M02", 6, false);

        // Buscar formas de pagamento persistidas
        FormaPagamento formaPagamentoDinheiro = getFormaPagamento(em, Tipo.DINHEIRO);
        FormaPagamento formaPagamentoCartao = getFormaPagamento(em, Tipo.CARTAO_CREDITO);

        // Verificar e criar reservas (baseado em cliente e mesa)
        if (!existeReserva(em, cliente1, mesa1)) {
            em.persist(new Reserva(cliente1, mesa1, new Date(), 2, true, formaPagamentoDinheiro, new BigDecimal("150.00"), Status.ATIVA));
        }

        if (!existeReserva(em, cliente2, mesa2)) {
            em.persist(new Reserva(cliente2, mesa2, new Date(), 4, false, formaPagamentoCartao, new BigDecimal("200.00"), Status.CONCLUIDA));
        }

        em.getTransaction().commit();
    }

    // Métodos auxiliares

    private static Cliente buscarOuCriarCliente(EntityManager em, String nome, String email) {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class);
        query.setParameter("email", email);
        var resultados = query.getResultList();
        if (resultados.isEmpty()) {
            Cliente novo = new Cliente(nome, email);
            em.persist(novo);
            return novo;
        } else {
            return resultados.get(0);
        }
    }

    private static boolean existeFormaPagamento(EntityManager em, Tipo tipo) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(fp) FROM FormaPagamento fp WHERE fp.tipo = :tipo", Long.class);
        query.setParameter("tipo", tipo);
        return query.getSingleResult() > 0;
    }

    private static FormaPagamento getFormaPagamento(EntityManager em, Tipo tipo) {
        TypedQuery<FormaPagamento> query = em.createQuery("SELECT fp FROM FormaPagamento fp WHERE fp.tipo = :tipo", FormaPagamento.class);
        query.setParameter("tipo", tipo);
        return query.getSingleResult();
    }

    private static Mesa buscarOuCriarMesa(EntityManager em, String numero, int lugares, boolean vip) {
        TypedQuery<Mesa> query = em.createQuery("SELECT m FROM Mesa m WHERE m.numero = :id", Mesa.class);
        query.setParameter("id", numero);
        var resultados = query.getResultList();
        if (resultados.isEmpty()) {
            Mesa nova = new Mesa(numero, lugares, vip);
            em.persist(nova);
            return nova;
        } else {
            return resultados.get(0);
        }
    }

    private static boolean existeReserva(EntityManager em, Cliente cliente, Mesa mesa) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.cliente = :cliente AND r.mesa = :mesa", Long.class);
        query.setParameter("cliente", cliente);
        query.setParameter("mesa", mesa);
        return query.getSingleResult() > 0;
    }
}
