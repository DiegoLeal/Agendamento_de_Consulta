package com.agendamentodeconsulta.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true)
    private String desc;

    public Perfil() {
        super();
    }
    public Perfil(long cod) {
    }
}
