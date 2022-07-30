package br.ufma.portal_egresso.entidade;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "faixa_salario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FaixaSalario {

    @Id
    @Column(name="id_faixa_salario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="descricao")
    private String descricao;

//    @OneToMany(mappedBy = "faixaSalario")
//    private List<ProfEgresso> profEgressos;
}
