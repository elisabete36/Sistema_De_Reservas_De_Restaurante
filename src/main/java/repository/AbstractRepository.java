package repository;

import jakarta.persistence.EntityManager;

public abstract class AbstractRepository<T> {
    protected EntityManager em;

    public AbstractRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(T entity) {
        em.getTransaction().begin();
        if (em.contains(entity)) {
            em.merge(entity);
        } else {
            em.persist(entity);
        }
        em.getTransaction().commit();
    }

    public void excluir(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    public T buscarPorId(Class<T> clazz, Long id) {
        return em.find(clazz, id);
    }
}
