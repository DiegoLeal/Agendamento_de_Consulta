package com.agendamentodeconsulta.unit;

import com.agendamentodeconsulta.model.Autocomplete;
import com.agendamentodeconsulta.model.Medico;
import com.agendamentodeconsulta.service.MedicoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicoServiceTest {

    @Autowired
    public MedicoService service;

    @BeforeEach
    public void setUp() {
        System.out.println("before: medicoServiceTest");
        Medico medico = criaMedico();
        service.salvar(medico);
    }

    @AfterEach
    public void setDown() {
        System.out.println("after: medicoServiceTest");
        service.getRepository().deleteAll();
    }

    @Test
    public void deveSalvarOMedico() {
        Medico medico = Medico.builder()
                .nome("teste")
                .email("teste@teste")
                .preco(BigDecimal.ONE)
                .crm("9999999")
                .consultorioQuarto(9999)
                .telefone("45 9999-9999")
                .build();

        Medico medicoSalvo = service.salvar(medico);

        assertNotNull(medicoSalvo);
        assertNotNull(medicoSalvo.getId());

        List<Medico> medicos = service.procuraTodos();

        boolean contains = medicos.stream()
                .anyMatch(medicoItem ->
                            medicoItem.getId().equals(medicoSalvo.getId()));

        assertTrue(contains);
    }

    @Test
    public void deveModificarOMedico() {
        Medico medico = service.procuraTodos().iterator().next();

        medico.setNome("nome-alterado");
        medico.setCrm("crm-alterado");
        medico.setEmail("email@alterado.com");
        medico.setPreco(BigDecimal.ZERO);
        medico.setConsultorioQuarto(9);

        Medico medicoAlterado = service.salvar(medico);

        assertEquals("nome-alterado", medicoAlterado.getNome());
        assertEquals("crm-alterado", medicoAlterado.getCrm());
        assertEquals("email@alterado.com", medicoAlterado.getEmail());
        assertEquals(BigDecimal.ZERO, medicoAlterado.getPreco());
        assertEquals(9, medicoAlterado.getConsultorioQuarto());
    }

    @Test
    public void deveEncontrarMedicoPorNome() {
        List<Medico> medicos = service.procuraPorString("test");

        assertEquals(1, medicos.size());

        Medico medico = medicos.iterator().next();

        assertEquals("teste", medico.getNome());
    }

    @Test
    void deveAutocompletarUmMedico() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Autocomplete> autocomplete = service.autocomplete(LocalDateTime.now(), "", pageRequest);
        assertFalse(autocomplete.getContent().isEmpty());
    }


    private Medico criaMedico() {
        Medico medico = Medico.builder()
                .nome("teste")
                .email("teste@teste")
                .preco(BigDecimal.ONE)
                .crm("9999999")
                .consultorioQuarto(9999)
                .telefone("45 9999-9999")
                .build();
        return service.salvar(medico);
    }


}
