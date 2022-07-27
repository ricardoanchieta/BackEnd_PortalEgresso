package br.ufma.portal_egresso.entidade;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Entity
@Table(name = "depoimento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Depoimento {
    @Id
    @Column(name="id_depoimento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "egresso_id")
    private Egresso egresso;

    @Column(name="texto")
    private String texto;

    @Column(name="data")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data;
}
