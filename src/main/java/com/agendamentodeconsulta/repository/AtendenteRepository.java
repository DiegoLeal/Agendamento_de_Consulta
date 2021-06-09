package com.agendamentodeconsulta.repository;

import com.agendamentodeconsulta.model.Atendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendenteRepository extends JpaRepository <Atendente, Long>{

    List<Atendente> findByNome(String nome);

    Atendente findOneByEmail(String email);

}
