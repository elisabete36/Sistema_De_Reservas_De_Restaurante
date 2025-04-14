package service;

import model.FormaPagamento;

import jakarta.persistence.EntityManager;  // Change from javax.persistence to jakarta.persistence
import jakarta.persistence.TypedQuery;   // Change from javax.persistence to jakarta.persistence
import java.util.List;

public class FormaPagamentoService {

    private final EntityManager entityManager;

    public FormaPagamentoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public FormaPagamento cadastrar(FormaPagamento formaPagamento) {
        entityManager.getTransaction().begin();
        entityManager.persist(formaPagamento);
        entityManager.getTransaction().commit();
        return formaPagamento;
    }

    public List<FormaPagamento> listarTodas() {
        TypedQuery<FormaPagamento> query = entityManager.createQuery("SELECT f FROM FormaPagamento f", FormaPagamento.class);
        return query.getResultList();
    }

    public List<FormaPagamento> buscarPorDescricao(String descricao) {
        TypedQuery<FormaPagamento> query = entityManager.createQuery(
                "SELECT f FROM FormaPagamento f WHERE LOWER(f.descricao) LIKE LOWER(:desc)", FormaPagamento.class);
        query.setParameter("desc", "%" + descricao + "%");
        return query.getResultList();
    }

    /**
     * Calcula o troco para pagamento em dinheiro
     * @param valorTotal Valor total da conta
     * @param valorPago Valor pago pelo cliente
     * @return Valor do troco
     * @throws IllegalArgumentException Se o valor pago for menor que o total
     */
    public double calcularTroco(double valorTotal, double valorPago) {
        if (valorPago < valorTotal) {
            throw new IllegalArgumentException("Valor pago não pode ser menor que o valor total");
        }
        return valorPago - valorTotal;
    }

    /**
     * Verifica se uma forma de pagamento é válida (cartão ou dinheiro)
     * @param formaPagamentoId ID da forma de pagamento
     * @return true se for válida
     */
    public boolean validarFormaPagamento(Long formaPagamentoId) {
        FormaPagamento formaPagamento = entityManager.find(FormaPagamento.class, formaPagamentoId);
        return formaPagamento != null;
    }
}
