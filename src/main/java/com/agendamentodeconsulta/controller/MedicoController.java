package com.agendamentodeconsulta.controller;

import com.agendamentodeconsulta.model.Autocomplete;
import com.agendamentodeconsulta.model.Medico;
import com.agendamentodeconsulta.service.MedicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("medico")
public class MedicoController extends DefaultController<Medico, MedicoService> {

    @GetMapping("/{id}/especialidade")
    public ResponseEntity<List<Medico>> ListarMedicsPorEspecialidade(@PathVariable Long id) {

        return ResponseEntity.ok(getService().findMedicosByEspecialidade(id));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Medico>> listaMedicosPorDisponibilidade(@RequestBody String data) {
        List<Medico> medicosAvailable = getService().findMedicoAvailabel(data);
        return ResponseEntity.ok(medicosAvailable);
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<Page<Autocomplete>> autocomplete(String date, String search, Pageable pageable) {
        LocalDateTime parseDate = LocalDateTime.parse(date);
        return ResponseEntity.ok(getService().autocomplete(parseDate, search, pageable));
    }

}


