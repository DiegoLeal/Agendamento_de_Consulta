package com.agendamentodeconsulta.service;

import com.agendamentodeconsulta.model.Especialidade;
import com.agendamentodeconsulta.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadeService extends DefaultService<Especialidade, EspecialidadeRepository> {

    @Override
    public List<Especialidade> procuraPorString(String search) {
        return getRepository().findAllByNomeContaining(search);
    }
}
