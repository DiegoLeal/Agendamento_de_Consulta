package com.agendamentodeconsulta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaChanges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private StatusConsulta status;

    private LocalDateTime date;

    @JoinColumn(name = "id_consulta")
    @ManyToOne
    @JsonBackReference
    private Consulta consulta;
}
