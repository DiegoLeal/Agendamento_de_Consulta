package com.agendamentodeconsulta.service;

import com.agendamentodeconsulta.model.Atendente;
import com.agendamentodeconsulta.repository.AtendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendenteService {

    private final AtendenteRepository atendenteRepository;

    @Autowired
    public AtendenteService(AtendenteRepository atendenteRepository) {

        this.atendenteRepository = atendenteRepository;
    }

    public List<Atendente> findByNome(String nome) {

        return atendenteRepository.findByNome(nome);
    }

    public Atendente adicionarAtendente(Atendente atendente) {

        return atendenteRepository.save(atendente);
    }

    public Atendente atualizarAtendente(Atendente atendente) {

       return atendenteRepository.save(atendente);
    }

    public void excluir(Long id) {

        atendenteRepository.deleteById(id);
    }

    public Atendente getUser(String username) {

        return atendenteRepository.findOneByEmail(username);
    }
}
