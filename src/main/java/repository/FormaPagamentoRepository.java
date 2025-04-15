package repository;

import jakarta.persistence.EntityManager;
import model.FormaPagamento;

import java.util.List;

public class FormaPagamentoRepository {
    private final EntityManager em;

    public FormaPagamentoRepository(EntityManager em) {
        this.em = em;
    }

    public List<FormaPagamento> listarAtivas() {
        return em.createQuery("SELECT f FROM FormaPagamento f WHERE f.ativo = true", FormaPagamento.class)
                .getResultList();
    }
}
