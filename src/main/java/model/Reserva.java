package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;

    @Column(nullable = false)
    private int quantidadePessoas;

    @Column(nullable = false)
    private boolean vip;

    @Column(nullable = false)
    private boolean pago;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)  // Referência ao Status
    @Column(name = "status", nullable = false)
    private Status status;  // A variável status do tipo Status enum

    public Reserva() {
    }

    public Reserva(Cliente cliente, Mesa mesa, Date dataHora, int quantidadePessoas,
                   boolean vip, FormaPagamento formaPagamento, BigDecimal valor, Status status) {
        this.cliente = cliente;
        this.mesa = mesa;
        this.dataHora = dataHora;
        this.quantidadePessoas = quantidadePessoas;
        this.vip = vip;
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.status = status;  // Atribuindo o status na criação
        this.pago = false;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Mesa getMesa() { return mesa; }
    public Date getDataHora() { return dataHora; }
    public int getQuantidadePessoas() { return quantidadePessoas; }
    public boolean isVip() { return vip; }
    public boolean isPago() { return pago; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public BigDecimal getValor() { return valor; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    // Métodos de negócio
    public BigDecimal calcularTroco(BigDecimal valorPago) {
        return valorPago.subtract(valor).max(BigDecimal.ZERO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", mesa=" + mesa +
                ", dataHora=" + dataHora +
                ", quantidadePessoas=" + quantidadePessoas +
                ", vip=" + vip +
                ", pago=" + pago +
                ", formaPagamento=" + formaPagamento +
                ", valor=" + valor +
                ", status=" + status +  // Status adicionado na saída
                '}';
    }
}
