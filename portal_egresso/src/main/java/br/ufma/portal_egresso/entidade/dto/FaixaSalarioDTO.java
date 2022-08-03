package br.ufma.portal_egresso.entidade.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaixaSalarioDTO {
    private Long id;
    private String descricao;
}