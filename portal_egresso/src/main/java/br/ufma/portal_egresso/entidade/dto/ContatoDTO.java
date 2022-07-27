package br.ufma.portal_egresso.entidade.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDTO {
    private Long id;
    private String nome;
    private String url_logo;
    private List<ContatoEgressoDTO> contatoEgresso;
}