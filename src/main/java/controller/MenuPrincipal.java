package controller;

import repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class MenuPrincipal {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemadeReservasdeRestaurante");
        EntityManager em = emf.createEntityManager();



        em.close();
        emf.close();
    }
}

