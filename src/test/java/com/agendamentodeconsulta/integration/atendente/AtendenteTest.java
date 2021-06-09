package com.agendamentodeconsulta.integration.atendente;

import com.agendamentodeconsulta.model.Atendente;
import com.agendamentodeconsulta.service.AtendenteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AtendenteTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private AtendenteService atendenteService;

    @Test
    void insereAtendenteTest() throws Exception {

        Atendente atendente = new Atendente();
        atendente.setNome("AtendenteTest01");
        atendente.setEmail("atendenteTest01@gmail.com");
        atendente.setTelefone("45 99999-9999");

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/atendente")
                .content(objectMapper.writeValueAsString(atendente))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void ListarTodosAtendentes_Test() throws  Exception {
        Atendente atendente = new Atendente();
        atendente.setNome("AtendenteTest01");
        atendente.setEmail("atendenteTest01@gmail.com");
        atendente.setTelefone("45 99999-9999");
        atendenteService.adicionarAtendente(atendente);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/atendente/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAtendenteByNome() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/atendente/nome/{nome}", "AtendenteTest01")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
