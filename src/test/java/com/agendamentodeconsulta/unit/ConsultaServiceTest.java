package com.agendamentodeconsulta.unit;

import com.agendamentodeconsulta.execptions.GenericException;
import com.agendamentodeconsulta.model.Consulta;
import com.agendamentodeconsulta.model.Paciente;
import com.agendamentodeconsulta.model.StatusConsulta;
import com.agendamentodeconsulta.service.ConsultaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.agendamentodeconsulta.model.StatusConsulta.EM_ANDAMENTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConsultaServiceTest {

    @Autowired
    public ConsultaService service;

    Consulta consulta;

    @BeforeEach
    public void setUp() {
        System.out.println("before: consultaServiceTest");
        consulta = criaConsulta();
        service.salvar(consulta);
    }

    @AfterEach
    public void setDown() {
        System.out.println("after: consultaServiceTest");
        service.getRepository().deleteAll();
    }

    @Test
    void deveSalvarAConsulta() {
        Consulta consulta = criaConsulta();
        Consulta consultaSalvo = service.salvar(consulta);

        assertNotNull(consultaSalvo.getId());
        assertEquals(BigDecimal.TEN, consultaSalvo.getPreco());
        assertEquals(LocalDateTime.of(2020, 1, 1, 12, 0), consultaSalvo.getDataConsulta());
        assertEquals(StatusConsulta.ABERTO, consultaSalvo.getStatus());
    }

    @Test
    void deveProcurarAConsulta() {
        List<Consulta> consultas = service.procuraTodos();
        Consulta consulta = consultas.iterator().next();

        assertNotNull(consulta);
    }

    @Test
    void deveEditarAConsulta() {
        Consulta consulta = service.procuraTodos().iterator().next();
        consulta.setDataConsulta(LocalDateTime.of(2021, 2, 2, 1, 30));
        consulta.setPreco(BigDecimal.valueOf(100.50));
        consulta.setStatus(EM_ANDAMENTO);

        Consulta salvo = service.salvar(consulta);
        assertEquals(BigDecimal.valueOf(100.50), salvo.getPreco());
        assertEquals(LocalDateTime.of(2021, 2, 2, 1, 30), salvo.getDataConsulta());
        assertEquals(EM_ANDAMENTO, salvo.getStatus());
    }

    @Test
    void deveIniciarAConsulta() {
        Assertions.assertDoesNotThrow(() -> service.iniciarConsulta(consulta.getId()));

        Consulta consulta = service.procuraPorId(this.consulta.getId());
        assertEquals(EM_ANDAMENTO, consulta.getStatus());

        boolean contains = consulta.getConsultaChanges()
                .stream()
                .anyMatch(consultaChanges ->
                        consultaChanges.getStatus().equals(EM_ANDAMENTO));
        assertTrue(contains);
    }

    @Test
    void naoDeveIniciarAConsulta() {
        Assertions.assertDoesNotThrow(() -> service.iniciarConsulta(consulta.getId()));

        Long id = consulta.getId();

        Assertions.assertThrows(GenericException.class, () ->
                service.iniciarConsulta(id));

    }

    private Consulta criaConsulta() {
        return Consulta.builder()
                .preco(BigDecimal.TEN)
                .paciente(Paciente.builder()
                        .email("jonas@asd.com")
                        .telefone("33333")
                        .nome("jonas")
                        .build())
                .dataConsulta(LocalDateTime.of(2020, 1, 1, 12, 0))
                .status(StatusConsulta.ABERTO)
                .build();
    }

}
