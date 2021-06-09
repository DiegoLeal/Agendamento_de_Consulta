package com.agendamentodeconsulta.service;

import com.agendamentodeconsulta.model.Endereco;
import com.agendamentodeconsulta.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService extends DefaultService<Endereco, EnderecoRepository> {

    @Override
    public List<Endereco> procuraPorString(@RequestParam String search) {
        List<Endereco> ruas = getRepository().findByRuaContaining(search);
        List<Endereco> bairros = getRepository().findByBairroContaining(search);
        List<Endereco> cidades = getRepository().findByCidadeContaining(search);
        List<Endereco> ufs = getRepository().findByUniaoFederativaContaining(search);

        if(ruas.isEmpty() && bairros.isEmpty() && cidades.isEmpty()){
            return ufs;
        }

        if(ruas.isEmpty() && bairros.isEmpty() && ufs.isEmpty()){
            return cidades;
        }

        if(ruas.isEmpty() && cidades.isEmpty() && ufs.isEmpty()){
            return bairros;
        }

        return ruas;

    }
}
