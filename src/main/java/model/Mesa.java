package model;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero; // <- novo campo
    private int quantidadeLugares;
    private boolean salaVip;

    public Mesa() {}

    public Mesa(int numero, int quantidadeLugares, boolean salaVip) {
        this.numero = numero;
        this.quantidadeLugares = quantidadeLugares;
        this.salaVip = salaVip;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getQuantidadeLugares() { return quantidadeLugares; }
    public void setQuantidadeLugares(int quantidadeLugares) { this.quantidadeLugares = quantidadeLugares; }

    public boolean isSalaVip() { return salaVip; }
    public void setSalaVip(boolean salaVip) { this.salaVip = salaVip; }

    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", numero=" + numero +
                ", quantidadeLugares=" + quantidadeLugares +
                ", salaVip=" + salaVip +
                '}';
    }
}
