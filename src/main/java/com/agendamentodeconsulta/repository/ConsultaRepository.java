package com.agendamentodeconsulta.repository;

import com.agendamentodeconsulta.model.Consulta;
import com.agendamentodeconsulta.model.Horario;
import com.agendamentodeconsulta.model.Medico;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByDataConsulta(LocalDateTime data);

    @Query("select h "
            + "from Horario h "
            + "where not exists("
            + "select a.horario.id "
            + "from Consulta a "
            + "where "
            + "a.medico.id = :id and "
            + "a.dataConsulta = :data and "
            + "a.horario.id = h.id "
            + ") "
            + "order by h.horaMinuto asc")
    List<Horario> findByMedicoIdAndDataNotHorarioAgendado(Long id, LocalDate data);
}
