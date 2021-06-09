package com.agendamentodeconsulta.repository;

import com.agendamentodeconsulta.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findAllByNomeContaining(String name);

}