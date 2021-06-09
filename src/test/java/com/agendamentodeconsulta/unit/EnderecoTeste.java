package com.agendamentodeconsulta.unit;

import com.agendamentodeconsulta.model.Endereco;
import com.agendamentodeconsulta.service.EnderecoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EnderecoTeste {

    @Autowired
    private EnderecoService endservice;

    @BeforeEach
    public void setUp() {
        System.out.println("before: enderecoServiceTest");
        Endereco endereco = criaEndereco();
        endservice.salvar(endereco);
    }

    @AfterEach
    public void setDown() {
        System.out.println("after: enderecoServiceTest");
        endservice.getRepository().deleteAll();
    }

    @Test
    public void insereEndereco(){
        Endereco endereco = Endereco.builder()
                .bairro("bairro_Teste")
                .cidade("cidade_Teste")
                .rua("")
                .uniaoFederativa("uf_Teste")
                .numero("123")
                .build();

        Endereco endSaved = endservice.salvar(endereco);

        assertNotNull(endSaved);
        assertNotNull(endSaved.getId());

    }

    @Test
    public void editarEndereco() {
        Endereco endereco = endservice.procuraTodos().iterator().next();

        endereco.setRua("alterado rua");
        endereco.setNumero("alterado numero");
        endereco.setBairro("alterado bairro");
        endereco.setCidade("alterado cidade");
        endereco.setUniaoFederativa("alterado uf");

        Endereco endAtualizado = endservice.salvar(endereco);

        assertEquals("alterado rua", endAtualizado.getRua());
        assertEquals("alterado numero", endAtualizado.getNumero());
        assertEquals("alterado bairro", endAtualizado.getBairro());
        assertEquals("alterado cidade", endAtualizado.getCidade());
        assertEquals("alterado uf", endAtualizado.getUniaoFederativa());
    }

    @Test
    public void procuraEnderecoPorRua() {
        List<Endereco> enderecos = endservice.procuraPorString("rua_teste");

        assertNotNull(enderecos);
    }

    @Test
    public void procuraEnderecoPorBairro() {
        List<Endereco> enderecos = endservice.procuraPorString("bairro_teste");

        assertNotNull(enderecos);
    }

    @Test
    public void procuraEnderecoPorCidade() {
        List<Endereco> enderecos = endservice.procuraPorString("cidade_teste");

        assertNotNull(enderecos);
    }

    @Test
    public void procuraEnderecoPorUF() {
        List<Endereco> enderecos = endservice.procuraPorString("uf_teste");

        assertNotNull(enderecos);
    }

    private Endereco criaEndereco(){
        Endereco endereco = Endereco.builder()
                .bairro("bairro_Teste")
                .cidade("cidade_Teste")
                .rua("rua_Teste")
                .uniaoFederativa("uf_Teste")
                .numero("123")
                .build();
        return endservice.salvar(endereco);
    }

}
