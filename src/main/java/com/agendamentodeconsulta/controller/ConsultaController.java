package com.agendamentodeconsulta.controller;

import com.agendamentodeconsulta.model.Consulta;
import com.agendamentodeconsulta.service.ConsultaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("consulta")
public class ConsultaController extends DefaultController<Consulta, ConsultaService> {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping("iniciar/{id}")
    public void iniciarConsulta(@PathVariable Long id) {
        getService().iniciarConsulta(id);
    }

    @PostMapping("concluir/{id}")
    public void concluirContaulta(@PathVariable Long id) {
        getService().concluirConsulta(id);
    }

    @GetMapping("/horario/medico/{id}/data/{data}")
    public ResponseEntity<?> horariosPorMededicos(@PathVariable("id") Long id,
                                         @PathVariable("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        return ResponseEntity.ok(consultaService.buscarHorariosNaoAgendadosPorMedicoIdEData(id, data));
    }
}
