package com.agendamentodeconsulta.unit;

import com.agendamentodeconsulta.model.Especialidade;
import com.agendamentodeconsulta.service.EspecialidadeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EspecialidadeServiceTest {

    @Autowired
    EspecialidadeService especialidadeService;

    @BeforeEach
    public void setUp() {
        System.out.println("before: EspecialidadeServiceTest");
        Especialidade especialidade = createEspecialidade();
        especialidadeService.salvar(especialidade);
    }

    @AfterEach
    public void setDown() {
        System.out.println("after: EspecialidadeServiceTest");
        especialidadeService.getRepository().deleteAll();
    }


    @Test
    public void deveSalvarEspecialidade() {
        Especialidade especialidade = Especialidade.builder()
                .nome("clinico geral")
                .build();

        Especialidade especialidadeSalvo = especialidadeService.salvar(especialidade);

        assertEquals("clinico geral", especialidadeSalvo.getNome());
        assertNotNull(especialidade.getId());
    }

    @Test
    public void deveEditarEspecialidade() {
        Especialidade especialidade = especialidadeService.procuraTodos().iterator().next();

        especialidade.setNome("alterado");
        Especialidade updatedEspecialidade = especialidadeService.salvar(especialidade);

        assertEquals("alterado", updatedEspecialidade.getNome());
    }

    @Test
    public void deveProcurarPorNome() {
        List<Especialidade> especialidades = especialidadeService.procuraPorString("es");

        assertEquals(1, especialidades.size());

        Especialidade especialidade = especialidades.iterator().next();

        assertNotNull(especialidade.getId());
        assertEquals("especialidade", especialidade.getNome());
    }

    public Especialidade createEspecialidade(){
        return Especialidade.builder()
                .nome("especialidade")
                .build();
    }

}
