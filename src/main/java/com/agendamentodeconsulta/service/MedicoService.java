package com.agendamentodeconsulta.service;

import com.agendamentodeconsulta.model.Autocomplete;
import com.agendamentodeconsulta.model.Medico;
import com.agendamentodeconsulta.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicoService extends DefaultService<Medico, MedicoRepository> {

    @Autowired
    ConsultaService consultaService;

    @Override
    public List<Medico> procuraPorString(String search) {
        return getRepository().findAllByNomeContaining(search);    }


    public List<Medico> findMedicosByEspecialidade(Long id) {
       return getRepository().findAllByEspecialidade_Id(id);
    }

    public List<Medico> findMedicoAvailabel(String data) {
        List<Long> medIds = consultaService.procuraMedicoPorDataConsulta(data);
        List<Medico> medAvailable = new ArrayList<Medico>();

        if(medIds.isEmpty()){
            medAvailable.addAll(getRepository().findAll());
            return medAvailable;
        }

        medAvailable.addAll(getRepository().findMedicoByIdNotIn(medIds));

        return medAvailable;
    }

    public Page<Autocomplete> autocomplete(LocalDateTime date, String search, Pageable pageable) {
        return getRepository().autocompleteMedicosPorDisponibilidadeDeHorario(date, search, pageable);
    }



}
