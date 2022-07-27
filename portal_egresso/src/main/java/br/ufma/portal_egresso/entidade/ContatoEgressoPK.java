package br.ufma.portal_egresso.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoEgressoPK implements Serializable {

    @Column(name = "id_egresso")
    private Long egressoId;

    @Column(name = "id_contato")
    private Long contatoId;

}
