package com.agendamentodeconsulta.repository;

import com.agendamentodeconsulta.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    List<Especialidade> findAllByNomeContaining(String nome);

}
