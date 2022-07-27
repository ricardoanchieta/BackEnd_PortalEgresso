package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EgressoRepo extends JpaRepository<Egresso, Long> {
    List<Egresso> findByEmail(String email);
    List<Egresso> findByEmailAndNome(String email, String nome);
    void deleteByCpf(String cpf);
    boolean existsByCpf(String cpf);
    List<Egresso> findByCpf(String cpf);

    boolean existsByEmail(String email);

}
