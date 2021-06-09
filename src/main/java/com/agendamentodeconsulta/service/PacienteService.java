package com.agendamentodeconsulta.service;

import com.agendamentodeconsulta.model.Paciente;
import com.agendamentodeconsulta.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService extends DefaultService<Paciente, PacienteRepository> {

    @Override
    public List<Paciente> procuraPorString(String search) {
        return getRepository().findAllByNomeContaining(search);
    }

    public void inserePaciente(Paciente paciente) {
    }
}
