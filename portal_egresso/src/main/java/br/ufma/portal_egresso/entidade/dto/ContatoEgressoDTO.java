package br.ufma.portal_egresso.entidade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoEgressoDTO {
    private Long contato_id;
    private Long egresso_id;
    private String descricao;
}