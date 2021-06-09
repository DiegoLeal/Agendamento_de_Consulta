package com.agendamentodeconsulta.controller;

import com.agendamentodeconsulta.service.DefaultService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class DefaultController<E, S extends DefaultService<E, ?>> {

    @Autowired
    @Getter
    private S service;

    @GetMapping
    public ResponseEntity<List<E>> findAll(String search) {
        if (search != null) {
            return ResponseEntity.ok(service.procuraPorString(search));
        }
        return ResponseEntity.ok(service.procuraTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<E> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.procuraPorId(id));
    }

    @PutMapping
    public ResponseEntity<E> update(@RequestBody E entity) {
        return ResponseEntity.ok(service.salvar(entity));
    }

    @PostMapping
    public ResponseEntity<E> save(@RequestBody E entity) {
        return new ResponseEntity<>(service.salvar(entity), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.deletarPorId(id);
    }


}
