package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.FaixaSalario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FaixaSalarioRepo extends JpaRepository<FaixaSalario,Long> {
  @Query(
    value = "SELECT count(e) " + 
            "FROM ((faixa_salario f INNER JOIN ProfEgresso p ON f.id_faixa_salario = :id_faixa_salario AND f.id_faixa_salario = p.faixa_salario_id) " +
            "INNER JOIN Egresso e ON p.egresso_id = e.id_egresso)",
    nativeQuery = true)
  public Long getQuantitativoEgresso(@Param("id_faixa_salario") Long idFaixaSalario);
}
