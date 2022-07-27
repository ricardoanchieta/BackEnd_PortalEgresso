package br.ufma.portal_egresso.entidade.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.portal_egresso.entidade.Cargo;

public interface CargoRepo extends JpaRepository<Cargo, Long> {

    List<Cargo> findByNomeContaining(String nome);
    void deleteByNome(String nome);

    @Query("select c from Cargo c ")
    List<Cargo> obterCargos();

    @Query("select c from Cargo c where c.nome=:nomeCargo")
    List<Cargo> obterCargoPorNome(
            @Param("nomeCargo") String nomeCargo);

}