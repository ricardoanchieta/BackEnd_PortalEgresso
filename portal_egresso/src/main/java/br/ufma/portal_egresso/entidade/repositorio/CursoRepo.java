package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Curso;
import br.ufma.portal_egresso.entidade.Egresso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CursoRepo extends JpaRepository<Curso,Long> {
    void deleteByNome(String nome);
    boolean existsByNome(String nome);
    List<Curso> findByNome(String nome);

    @Query("select c from Curso c ")
    List<Curso> obterCursos();

    @Query("select c from Curso c where c.nivel=:nivelCurso")
    List<Curso> obterCursosPorNivel(
            @Param("nivelCurso") String nivelCurso);
}
