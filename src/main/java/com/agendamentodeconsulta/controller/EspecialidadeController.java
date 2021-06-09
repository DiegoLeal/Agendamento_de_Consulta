package com.agendamentodeconsulta.controller;

import com.agendamentodeconsulta.model.Especialidade;
import com.agendamentodeconsulta.service.EspecialidadeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/especialidade")
public class EspecialidadeController extends DefaultController<Especialidade, EspecialidadeService> {
}
