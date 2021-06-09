package com.agendamentodeconsulta.unit;

import com.agendamentodeconsulta.model.Paciente;
import com.agendamentodeconsulta.service.PacienteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    public PacienteService service;

    @BeforeEach
    public void setUp() {
        System.out.println("before: pacienteServiceTest");
        Paciente paciente = criaPaciente();
        service.salvar(paciente);
    }

    @AfterEach
    public void setDown() {
        System.out.println("after: pacienteServiceTest");
        service.getRepository().deleteAll();
    }

    @Test
    public void deveSalvarOPaciente() {
        Paciente paciente = Paciente.builder()
                .nome("teste")
                .email("teste@teste")
                .telefone("45 9999-9999")
                .build();

        Paciente pacienteSalvo = service.salvar(paciente);

        assertNotNull(pacienteSalvo);
        assertNotNull(pacienteSalvo.getId());

        List<Paciente> pacientes = service.procuraTodos();

        boolean contains = pacientes.stream()
                .anyMatch(pacientesItem ->
                        pacientesItem.getId().equals(pacienteSalvo.getId()));

        assertTrue(contains);
    }

    @Test
    public void deveModificarOPaciente() {
        Paciente paciente = service.procuraTodos().iterator().next();

        paciente.setNome("nome-alterado");
       paciente.setEmail("email@alterado.com");


        Paciente pacienteAlterado = service.salvar(paciente);

        assertEquals("nome-alterado", pacienteAlterado.getNome());
        assertEquals("email@alterado.com", pacienteAlterado.getEmail());
       }

    @Test
    public void deveEncontrarPacientePorNome() {
        List<Paciente> paciente = service.procuraPorString("test");

        assertEquals(1, paciente.size());

        Paciente medico = paciente.iterator().next();

        assertEquals("teste", medico.getNome());
    }


    private Paciente criaPaciente() {
        Paciente paciente = Paciente.builder()
                .nome("teste")
                .email("teste@teste")
                .telefone("45 9999-9999")
                .build();
        return service.salvar(paciente);
    }


}

