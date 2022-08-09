package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Curso;
import br.ufma.portal_egresso.entidade.CursoEgresso;
import br.ufma.portal_egresso.entidade.CursoEgressoPK;
import br.ufma.portal_egresso.entidade.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CursoEgressoRepo extends JpaRepository<CursoEgresso,Long> {
    List<CursoEgresso> findByEgresso(Egresso egresso);
    List<CursoEgresso> findByCurso(Curso curso);
    void deleteByEgresso(Egresso egresso);
    boolean existsByCurso(Curso curso);
    boolean existsByEgresso(Egresso egresso);

    Optional<CursoEgresso> findById(CursoEgressoPK id);

//    @Query("select ce from CursoEgresso ce where ce.cursoId=:curso_id")
//    List<CursoEgresso> getQuantDeEgressoPorCurso(
//            @Param("curso_id") Long curso_id);

    @Query("SELECT egressos from CursoEgresso egressos")
    List<CursoEgresso> getAllEgressosComCurso();
}
