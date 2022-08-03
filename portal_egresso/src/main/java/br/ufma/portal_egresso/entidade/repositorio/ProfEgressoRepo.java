package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Cargo;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.FaixaSalario;
import br.ufma.portal_egresso.entidade.ProfEgresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfEgressoRepo extends JpaRepository<ProfEgresso,Long> {
    List<ProfEgresso> findByEmpresaContaining(String empresa);
    List<ProfEgresso> findByEgresso(Egresso egresso);
    List<ProfEgresso> findByCargo(Cargo cargo);
    List<ProfEgresso> findByFaixaSalario(FaixaSalario faixa_salario);
}
