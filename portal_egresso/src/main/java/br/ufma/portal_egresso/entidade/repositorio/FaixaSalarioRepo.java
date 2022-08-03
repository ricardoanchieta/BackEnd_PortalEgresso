package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.FaixaSalario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaixaSalarioRepo extends JpaRepository<FaixaSalario,Long> {
  List<FaixaSalario> findByDescricao(String descricao);

  @Query("select c from FaixaSalario c ")
  List<FaixaSalario> obterSalarios();
}
