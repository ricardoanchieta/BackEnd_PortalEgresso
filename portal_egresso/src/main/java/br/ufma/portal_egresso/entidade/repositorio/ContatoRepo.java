package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Contato;
import br.ufma.portal_egresso.entidade.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContatoRepo extends JpaRepository<Contato, Long> {

    List<Contato> findByNomeContaining(String nome);
    void deleteByNome(String nome);

    @Query("select c from Contato c ")
    List<Contato> obterContatos();
}
