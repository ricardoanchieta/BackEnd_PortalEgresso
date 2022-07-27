package br.ufma.portal_egresso.entidade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="prof_egresso")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProfEgresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prof_egresso")
    private Long id;

    @ManyToOne
    @JoinColumn(name="egresso_id")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "faixa_salario_id")
    private FaixaSalario faixaSalario;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_registro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataRegistro;

}
