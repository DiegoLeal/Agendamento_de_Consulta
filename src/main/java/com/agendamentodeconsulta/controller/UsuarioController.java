package com.agendamentodeconsulta.controller;

;
import com.agendamentodeconsulta.model.Usuario;
import com.agendamentodeconsulta.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "https://localhost:3000")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {

        return usuarioService.adicionarUsuario(usuario);
    }
}
