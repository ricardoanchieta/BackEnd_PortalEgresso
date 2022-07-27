package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EgressoRepo extends JpaRepository<Egresso, Long> {
    Egresso findByEmail(String email);


    List<Egresso> findByNomeContaining(String nome);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);
    long countByNome(String nome);

//    @Query("select u from Egresso u where u.nome=:nomeEgresso")
//    List<Egresso> getAllEgressosByNome(
//            @Param("nomeEgresso") String nomeEgresso);

    @Query("SELECT egressos.nome from Egresso egressos")
    List<String> getAllEgressos();
}
