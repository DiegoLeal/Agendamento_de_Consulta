package com.agendamentodeconsulta.integration.consulta;

import com.agendamentodeconsulta.execptions.GenericException;
import com.agendamentodeconsulta.model.*;
import com.agendamentodeconsulta.service.ConsultaService;
import com.agendamentodeconsulta.service.EspecialidadeService;
import com.agendamentodeconsulta.service.MedicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
//@ExtendWith(SpringExtension.class) // spring boot >2.1 não é necessário, pois já está incluso na annotation @SpringBootTest
class ConsultaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadeService especialidadeService;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Executa antes de cada teste, inserindo os cadastros necessários para
     * o cadastro da consulta.
     */
    @BeforeEach
    void setUp() {
        medicoService.getRepository().deleteAll();
        especialidadeService.getRepository().deleteAll();

        criaEspecialidade();
        criaMedico();
    }

    /**
     * Executa depois de cada teste, removendo tudo que foi cadastrado.
     */
    @AfterEach
    void setDown() {
        consultaService.getRepository().deleteAll();
        medicoService.getRepository().deleteAll();
        especialidadeService.getRepository().deleteAll();
    }

    @Test
    void shouldListConsultas() throws Exception {
        mockMvc.perform(get("/consulta"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                ));
    }

    @Test
    void deve_abrir_consulta_sem_cadastro_de_paciente() throws Exception {
        Consulta consulta = criaConsulta();

        mockMvc.perform(
                post("/consulta")
                        .content(objectMapper.writeValueAsString(consulta))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(matchAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON)
                ));
    }


    @Test
    void deve_iniciar_e_finalizar_consulta() throws Exception {
        Consulta consulta = criaConsulta();
        salvaConsulta(consulta);

        Consulta consultaSalva = procurarQualquerConsulta();

        mockMvc.perform(
                post("/consulta/iniciar/{id}", consultaSalva.getId()))
                .andExpect(matchAll(
                        status().isOk()
                ));

        mockMvc.perform(
                post("/consulta/concluir/{id}", consultaSalva.getId()))
                .andExpect(matchAll(
                        status().isOk()
                ));
    }

    @Test
    void deve_validar_disponibilidade_ao_marcar_a_consulta() throws Exception {
        Consulta consultaA = criaConsulta();
        Consulta consultaB = criaConsulta();

        salvaConsulta(consultaA)
                .andExpect(matchAll(
                        status().isCreated()
                ));

        mockMvc.perform(post("/consulta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consultaB)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof GenericException))
                .andExpect(result -> assertEquals("error.consulta.medico_not_available", result.getResolvedException().getMessage()));
    }


    private ResultActions salvaConsulta(Consulta consulta) throws Exception {
        return mockMvc.perform(post("/consulta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consulta)));
    }

    private Consulta procurarQualquerConsulta() {
        return consultaService.procuraTodos().iterator().next();
    }

    private void criaMedico() {
        Especialidade especialidade =
                especialidadeService.procuraPorString("geral").iterator().next();
        medicoService.salvar(
                Medico.builder()
                        .nome("jonas")
                        .telefone("999999999")
                        .crm("9999999")
                        .preco(BigDecimal.ONE)
                        .email("jonas@hotmail.com")
                        .especialidade(especialidade)
                        .build()
        );
    }

    private void criaEspecialidade() {
        especialidadeService.salvar(Especialidade.builder().nome("geral").build());
    }

    private Consulta criaConsulta() {
        Medico medico = medicoService.procuraPorString("jonas")
                .iterator().next();

        return Consulta.builder()
                .preco(BigDecimal.valueOf(120.00))
                .dataConsulta(LocalDateTime.of(2020, 10, 10, 1, 30))
                .medico(medico)
                .atendente(null)
                .paciente(Paciente.builder()
                        .nome("jonas farias")
                        .telefone("3030-3030")
                        .email("jonas@hotmail.com")
                        .build())
                .build();
    }
}
