package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Depoimento;
import br.ufma.portal_egresso.entidade.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepoimentoRepo extends JpaRepository<Depoimento, Long> {
    List<Depoimento> findByEgresso(Egresso egresso);
    boolean existsByEgresso(Egresso egresso);
    void deleteByEgresso(Egresso egresso);

//    @Query("select dep from Depoimento dep where dep.egresso_id=:egresso_id")
//    List<Depoimento> getAllDepoimentosByIdEgresso(
//            @Param("egresso_id") long egresso_id);

    List<Depoimento> findByEgressoId(long egresso_id);
}
