package br.ufma.portal_egresso.entidade;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Cargo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Cargo {

    @Id
    @Column(name="id_cargo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="descricao")
    private String descricao;

    @OneToMany(mappedBy = "cargo")
    private List<ProfEgresso> profEgressos;
}
