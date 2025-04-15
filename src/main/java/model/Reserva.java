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

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora", nullable = false)
    private Date dataHora;

    @Column(nullable = false)
    private int quantidadePessoas;

    @Column(nullable = false)
    private boolean vip;

    @Column(nullable = false)
    private boolean pago;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // Construtores
    public Reserva() {}

    public Reserva(Cliente cliente, Mesa mesa, Date dataHora, int quantidadePessoas,
                   boolean vip, FormaPagamento formaPagamento, BigDecimal valor, Status status) {
        this.cliente = cliente;
        this.mesa = mesa;
        this.dataHora = dataHora;
        this.quantidadePessoas = quantidadePessoas;
        this.vip = vip;
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.status = status;
        this.pago = false;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }
    public Date getDataHora() { return dataHora; }
    public void setDataHora(Date dataHora) { this.dataHora = dataHora; }
    public int getQuantidadePessoas() { return quantidadePessoas; }
    public void setQuantidadePessoas(int quantidadePessoas) { this.quantidadePessoas = quantidadePessoas; }
    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }
    public boolean isPago() { return pago; }
    public void setPago(boolean pago) { this.pago = pago; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    // Métodos de negócio
    public BigDecimal calcularTroco(BigDecimal valorPago) {
        return valorPago.subtract(valor).max(BigDecimal.ZERO);
    }

    // equals e hashCode com base no id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva)) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString
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
                ", status=" + status +
                '}';
    }
}
