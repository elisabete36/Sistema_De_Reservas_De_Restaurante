package repository;

import jakarta.persistence.EntityManager;
import model.Reserva;
import model.Status;

import java.util.Date;
import java.util.List;

public class ReservaRepository {
    private final EntityManager em;

    public ReservaRepository(EntityManager em) {
        this.em = em;
    }

    public List<Reserva> findWithClientes() {
        return em.createQuery("SELECT r FROM Reserva r JOIN FETCH r.cliente", Reserva.class)
                .getResultList();
    }

    public List<Reserva> findByDataBetween(Date inicio, Date fim) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.dataHora BETWEEN :inicio AND :fim", Reserva.class)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();
    }

    public long countAll() {
        return em.createQuery("SELECT COUNT(r) FROM Reserva r", Long.class)
                .getSingleResult();
    }

    public List<Reserva> listarPorStatus(Status status) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.status = :status", Reserva.class)
                .setParameter("status", status)
                .getResultList();
    }
}
