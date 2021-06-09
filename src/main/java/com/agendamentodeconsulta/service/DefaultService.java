package com.agendamentodeconsulta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultService<E, R extends JpaRepository<E, Long>> {

    @Autowired
    private R repository;

    @Transactional
    public E salvar(E entidade) {
        antesDeSalvar(entidade);
        return repository.save(entidade);
    }

    public E procuraPorId(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public List<E> procuraTodos() {
        return repository.findAll();
    }

    @Transactional
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

    public R getRepository() {
        return repository;
    }

    public List<E> procuraPorString(String search) {
        return List.of();
    }

    public void antesDeSalvar(E entidade) {
        // executa antes de alguma alteração na entidade
    }
}
