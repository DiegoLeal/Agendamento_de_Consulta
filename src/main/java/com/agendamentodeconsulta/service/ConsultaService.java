package com.agendamentodeconsulta.service;

import com.agendamentodeconsulta.execptions.GenericException;
import com.agendamentodeconsulta.model.*;
import com.agendamentodeconsulta.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.agendamentodeconsulta.model.StatusConsulta.CONCLUIDA;
import static com.agendamentodeconsulta.model.StatusConsulta.EM_ANDAMENTO;

@Service
@RequiredArgsConstructor
public class ConsultaService extends DefaultService<Consulta, ConsultaRepository> {

    private final PacienteService pacienteService;

    private final ConsultaRepository consultaRepository;

    @Override
    public void antesDeSalvar(Consulta entidade) {
        if(Objects.isNull(entidade.getId())) {
            entidade.setStatus(StatusConsulta.ABERTO);
        }

        salvarPacienteNaoCadastrado(entidade);
    }

    public void iniciarConsulta(Long id) {
        Consulta consulta = getConsultaById(id);

        if (!consulta.getStatus().equals(StatusConsulta.ABERTO)) {
            throw new GenericException("error.consulta.not_open");
        }

        consulta.setStatus(EM_ANDAMENTO);
        consulta.getConsultaChanges()
                .add(ConsultaChanges.builder()
                .consulta(consulta)
                .status(EM_ANDAMENTO)
                .date(LocalDateTime.now())
                .build());
        getRepository().save(consulta);
    }

    public void concluirConsulta(Long id) {
        Consulta consulta = getConsultaById(id);

        if(!consulta.getStatus().equals(EM_ANDAMENTO)) {
            throw new GenericException("error.consulta.not_in_progress");
        }

        consulta.setStatus(CONCLUIDA);
        consulta.getConsultaChanges()
                .add(ConsultaChanges.builder()
                        .status(CONCLUIDA)
                        .date(LocalDateTime.now())
                        .consulta(consulta)
                        .build());

        getRepository().save(consulta);
    }

    private Consulta getConsultaById(Long id) {
        return getRepository()
                .findById(id)
                .orElseThrow(() -> new GenericException("error.consulta.not_founded"));
    }

    private void salvarPacienteNaoCadastrado(Consulta consulta) {
        if (Objects.nonNull(consulta.getPaciente().getId())) {
            return;
        }

        Paciente paciente = pacienteService.salvar(consulta.getPaciente());
        consulta.setPaciente(paciente);
    }

    public List<Long> procuraMedicoPorDataConsulta(String data) {
        LocalDateTime dateTime = LocalDateTime.parse(data);
        List<Consulta> consultas = getRepository().findByDataConsulta(dateTime);
        List<Long> medIds =  new ArrayList<Long>();

        for (Consulta consulta : consultas) {
            medIds.add(consulta.getMedico().getId());
        }

        return medIds;
    }

    public List<Horario> buscarHorariosNaoAgendadosPorMedicoIdEData(Long id, LocalDate data) {

        return consultaRepository.findByMedicoIdAndDataNotHorarioAgendado(id, data);
    }
}
