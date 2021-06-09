package com.agendamentodeconsulta.controller;

import com.agendamentodeconsulta.model.Endereco;
import com.agendamentodeconsulta.service.EnderecoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("endereco")
public class EnderecoController extends DefaultController<Endereco, EnderecoService> {
}
