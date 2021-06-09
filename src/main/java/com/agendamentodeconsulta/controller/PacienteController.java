package com.agendamentodeconsulta.controller;

import com.agendamentodeconsulta.model.Paciente;
import com.agendamentodeconsulta.service.PacienteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("paciente")
public class PacienteController extends DefaultController<Paciente, PacienteService>{
}

