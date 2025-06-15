package com.example.myfirstapp.zip.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

// Não precisamos mais do JsonIgnore aqui se for apenas para o getter de 'order'
// import com.fasterxml.jackson.annotation.JsonIgnore; 

import jakarta.persistence.Entity;
import jakarta.persistence.Id; // Apenas @Id, sem @GeneratedValue
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_payment")
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // Apenas @Id, o ID virá do Order por causa do @MapsId
    private Long id;

    private Instant moment;

    // Mantemos @OneToOne e @MapsId para o mapeamento da chave primária compartilhada
    @OneToOne
    @MapsId
    private Order order;

    public Payment() {
    }

    public Payment(Long id, Instant moment, Order order) {
        super();
        this.id = id;
        this.moment = moment;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    // REMOVEMOS O @JsonIgnore AQUI!
    // Isso permite que o Jackson desserialize o objeto Order quando ele vier no JSON de entrada
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Payment other = (Payment) obj;
        return Objects.equals(id, other.id);
    }
}