package br.ufma.portal_egresso.entidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DepoimentoDTO {
    private Long id;
    private Long id_egresso;
    private String texto;
    private LocalDate data;
}
