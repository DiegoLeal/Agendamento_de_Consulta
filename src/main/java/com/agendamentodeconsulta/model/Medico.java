package com.agendamentodeconsulta.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String nome;

    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @JoinColumn(name = "id_especialidade")
    @ManyToOne
    private Especialidade especialidade;

    private Integer consultorioQuarto;

    private String telefone;

    private String crm;

    private BigDecimal preco;
}
