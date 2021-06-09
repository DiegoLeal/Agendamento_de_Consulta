package com.agendamentodeconsulta.controller;

import com.agendamentodeconsulta.model.Atendente;
import com.agendamentodeconsulta.repository.AtendenteRepository;
import com.agendamentodeconsulta.service.AtendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "atendente")
public class AtendenteController {

    private final AtendenteRepository atendenteRepository;

    private final AtendenteService atendenteService;

    @Autowired
    public AtendenteController(AtendenteRepository atendenteRepository, AtendenteService atendenteService) {
        this.atendenteRepository = atendenteRepository;
        this.atendenteService = atendenteService;
    }

    @GetMapping("/all")
    public List<Atendente> listAll() {
        return atendenteRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Atendente> buscar(@PathVariable Long id) {
        Optional<Atendente> atendente = atendenteRepository.findById(id);

        return atendente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("nome/{nome}")
    public ResponseEntity<List<Atendente>> findByNome(@PathVariable String nome) {
        return ResponseEntity.ok(atendenteService.findByNome(nome));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Atendente adicionarAtendente(@RequestBody Atendente atendente) {

        return atendenteService.adicionarAtendente(atendente);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Atendente> atualizar(@PathVariable Long id, @RequestBody Atendente atendente) {
        if (!atendenteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        atendente.setId(id);
        atendente = atendenteService.atualizarAtendente(atendente);

        return ResponseEntity.ok(atendente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!atendenteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        atendenteService.excluir(id);
            return ResponseEntity.ok().build();
    }
}
