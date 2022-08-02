package br.ufma.portal_egresso.entidade.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.portal_egresso.entidade.Contato;
import br.ufma.portal_egresso.entidade.ContatoEgresso;
import br.ufma.portal_egresso.entidade.Egresso;

public interface ContatoEgressoRepo extends JpaRepository<ContatoEgresso, Long> {

    List<ContatoEgresso> findByEgresso(Egresso egresso);
    List<ContatoEgresso> findByContato(Contato contato);
//    List<ContatoEgresso> findByDescricao(String descricao);
    void deleteByEgresso(Egresso egresso);

}