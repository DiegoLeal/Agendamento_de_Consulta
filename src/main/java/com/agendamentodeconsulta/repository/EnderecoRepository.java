package com.agendamentodeconsulta.repository;

import com.agendamentodeconsulta.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByBairroContaining(String search);
    List<Endereco> findByRuaContaining(String search);
    List<Endereco> findByCidadeContaining(String search);
    List<Endereco> findByUniaoFederativaContaining(String search);

}
