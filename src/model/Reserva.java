package com.hotel.reservas.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private Long habitacionId;

    @Column(nullable = false)
    private LocalDate desde;

    @Column(nullable = false)
    private LocalDate hasta;

    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;

    private LocalDateTime fechaCreacion;

    public Reserva() { }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getHabitacionId() { return habitacionId; }
    public void setHabitacionId(Long habitacionId) { this.habitacionId = habitacionId; }

    public LocalDate getDesde() { return desde; }
    public void setDesde(LocalDate desde) { this.desde = desde; }

    public LocalDate getHasta() { return hasta; }
    public void setHasta(LocalDate hasta) { this.hasta = hasta; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (this.estado == null) {
            this.estado = EstadoReserva.PENDIENTE;
        }
    }
}
