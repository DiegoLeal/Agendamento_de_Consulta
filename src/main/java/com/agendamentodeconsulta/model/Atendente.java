package com.agendamentodeconsulta.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 45)
    private String nome;

    @Size(max = 20)
    private String telefone;

    @Email
    @Size(max = 45)
    private String email;

    @OneToMany(mappedBy = "atendente")
    private List<Consulta> consultas;

    public List<Consulta> getConsultas() {
        return consultas;
    }
}


