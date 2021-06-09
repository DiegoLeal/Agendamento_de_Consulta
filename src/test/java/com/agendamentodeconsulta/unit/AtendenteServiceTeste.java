package com.agendamentodeconsulta.unit;

import com.agendamentodeconsulta.model.Atendente;
import com.agendamentodeconsulta.service.AtendenteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AtendenteServiceTeste {

    @Autowired
    public AtendenteService atendenteService;

    @BeforeEach
    public void setUp() {
        System.out.println("before: atendenteServiceTest");
        Atendente atendente = criarAtendente();
        atendenteService.adicionarAtendente(atendente);
    }

    @Test
    public Atendente criarAtendente() {
        Atendente atendente = new Atendente();
        atendente.setNome("test");
        atendente.setTelefone("45 99999-9999");
        atendente.setEmail("test@test");

        Atendente salvarAtendente = atendenteService.adicionarAtendente(atendente);

        Assertions.assertEquals(atendente, salvarAtendente);
        return atendente;
    }
}


