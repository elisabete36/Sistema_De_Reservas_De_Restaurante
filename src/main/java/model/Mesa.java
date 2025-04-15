package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Número da mesa é obrigatório")
    @Column(nullable = false, unique = true, length = 10)
    private String numero; // Campo número

    @Min(value = 1, message = "Capacidade mínima é 1")
    @Max(value = 20, message = "Capacidade máxima é 20")
    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private boolean vip;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    // Construtores
    public Mesa() {}

    public Mesa(String numero, int capacidade, boolean vip) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.vip = vip;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }  // Acessar número
    public void setNumero(String numero) { this.numero = numero; }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }

    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }

    // equals e hashCode com base no id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mesa)) return false;
        Mesa mesa = (Mesa) o;
        return Objects.equals(id, mesa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString detalhado
    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", capacidade=" + capacidade +
                ", vip=" + vip +
                '}';
    }
}
