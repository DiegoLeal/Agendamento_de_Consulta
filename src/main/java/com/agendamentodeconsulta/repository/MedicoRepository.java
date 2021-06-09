package com.agendamentodeconsulta.repository;

import com.agendamentodeconsulta.model.Autocomplete;
import com.agendamentodeconsulta.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    List<Medico> findAllByNomeContaining(String name);

    List<Medico> findAllByEspecialidade_Id(Long id);

    List<Medico> findMedicoByIdNotIn(List<Long> ids);

    @Query("select new com.agendamentodeconsulta.model.Autocomplete(m.id, m.nome) " +
            "from Medico m " +
            "where m.id not in (select c.medico.id from Consulta c where c.dataConsulta = ?1) " +
            "  and upper(m.nome) like concat('%', upper(?2), '%')")
    Page<Autocomplete> autocompleteMedicosPorDisponibilidadeDeHorario(LocalDateTime data, String search, Pageable pageable);

}
