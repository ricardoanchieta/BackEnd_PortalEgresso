package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Curso;
import br.ufma.portal_egresso.entidade.CursoEgresso;
import br.ufma.portal_egresso.entidade.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoEgressoRepo extends JpaRepository<CursoEgresso,Long> {
    List<CursoEgresso> findByEgresso(Egresso egresso);
    List<CursoEgresso> findByCurso(Curso curso);
    void deleteByEgresso(Egresso egresso);
    boolean existsByCurso(Curso curso);
    boolean existsByEgresso(Egresso egresso);
}
