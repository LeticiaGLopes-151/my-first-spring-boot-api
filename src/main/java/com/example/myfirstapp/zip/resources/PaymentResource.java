package com.example.myfirstapp.zip.resources;

import java.net.URI; // Importe esta classe
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; // Importe esta classe
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Importe esta classe
import org.springframework.web.bind.annotation.PutMapping; // Importe esta classe
import org.springframework.web.bind.annotation.RequestBody; // Importe esta classe
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Importe esta classe

import com.example.myfirstapp.zip.entities.Payment;
import com.example.myfirstapp.zip.services.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

    @Autowired
    private PaymentService service;

    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        List<Payment> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Payment> findById(@PathVariable Long id) {
        Payment obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping // Endpoint para inserir um novo pagamento
    public ResponseEntity<Payment> insert(@RequestBody Payment obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}") // Endpoint para deletar um pagamento
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}") // Endpoint para atualizar um pagamento
    public ResponseEntity<Payment> update(@PathVariable Long id, @RequestBody Payment obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}