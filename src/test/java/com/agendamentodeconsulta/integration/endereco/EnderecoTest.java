package com.agendamentodeconsulta.integration.endereco;

import com.agendamentodeconsulta.model.Endereco;
import com.agendamentodeconsulta.service.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class EnderecoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EnderecoService enderecoService;

    @Test
    void insertEndereco_Test() throws Exception {
        Endereco end = new Endereco();
        end.setRua("Avenida Iguaçu");
        end.setNumero("828");
        end.setBairro("Vila Yolanda");
        end.setCidade("Foz do Iguaçu");
        end.setUniaoFederativa("PR");

        this.mockMvc.perform(MockMvcRequestBuilders
            .post("/endereco")
            .content(objectMapper.writeValueAsString(end))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void findAllEnderecos_Test() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
        .get("/endereco")
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
