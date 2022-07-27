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

    @Query(
        value = "SELECT e " + 
                "FROM ((Egresso e INNER JOIN CursoEgresso ce ON e.id_egresso = ce.egresso_id) " +
                "INNER JOIN Curso c ON c.id_curso = ce.curso_id AND c.id_curso = :id_curso)", 
        nativeQuery = true)
    public List<Egresso> consultarEgressos(@Param("id_curso") Long idCurso);

    @Query(
        value = "SELECT count(e) " + 
                "FROM ((Egresso e INNER JOIN CursoEgresso ce ON e.id_egresso = ce.egresso_id) " +
                "INNER JOIN Curso c ON c.id_curso = ce.curso_id AND c.id_curso = :id_curso)", 
        nativeQuery = true)
    public Long quantitativoEgressos(@Param("id_curso") Long idCurso);
}
