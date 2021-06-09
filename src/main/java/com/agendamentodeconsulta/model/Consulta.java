package com.agendamentodeconsulta.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataConsulta;

    private BigDecimal preco;

    @JoinColumn(name = "id_paciente")
    @ManyToOne
    private Paciente paciente;

    @JoinColumn(name = "id_medico")
    @ManyToOne
    private Medico medico;

    @JoinColumn(name = "id_atendente")
    @ManyToOne
    private Atendente atendente;

    @ManyToOne
    @JoinColumn(name="id_horario")
    private Horario horario;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status;

    @OneToMany(cascade = ALL, mappedBy = "consulta", fetch = FetchType.EAGER)
    @JsonIdentityReference
    private Set<ConsultaChanges> consultaChanges;

}
