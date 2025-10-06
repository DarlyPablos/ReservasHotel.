package com.hotel.reservas.controller;

import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.model.EstadoReserva;
import com.hotel.reservas.repository.ReservaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaRepository repo;

    public ReservaController(ReservaRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva r) {
        // Validaciones simples
        if (r.getClienteId() == null || r.getHabitacionId() == null || r.getDesde() == null || r.getHasta() == null) {
            return ResponseEntity.badRequest().body("Campos requeridos: clienteId, habitacionId, desde, hasta");
        }
        if (r.getHasta().isBefore(r.getDesde())) {
            return ResponseEntity.badRequest().body("El campo 'hasta' debe ser posterior o igual a 'desde'.");
        }

        // Lógica mínima: marcar CONFIRMADA y guardar
        r.setEstado(EstadoReserva.CONFIRMADA);
        Reserva saved = repo.save(r);
        URI location = URI.create("/api/v1/reservas/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtener(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Reserva> listar(@RequestParam(required = false) Long clienteId) {
        if (clienteId != null) {
            return repo.findByClienteId(clienteId);
        }
        return repo.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
