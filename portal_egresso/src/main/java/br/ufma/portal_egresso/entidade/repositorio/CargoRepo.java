package br.ufma.portal_egresso.entidade.repositorio;

import br.ufma.portal_egresso.entidade.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CargoRepo extends JpaRepository<Cargo, Long> {

    List<Cargo> findByNome(String nome);
    boolean existsByNome(String nome);


    @Query(
        value = "SELECT c " + 
                "FROM ((Cargo c INNER JOIN ProfEgresso p ON c.id_cargo = p.cargo_id) " +
                "INNER JOIN Egresso e ON p.egresso_id = e.id_egresso AND e.id_egresso = :egresso)", 
        nativeQuery = true)
    public Cargo consultarCargo(@Param("egresso") Long idEgresso);

    @Query(
        value = "SELECT count(e) " + 
                "FROM ((Cargo c INNER JOIN ProfEgresso p ON c.id_cargo = p.cargo_id AND c.id_cargo = :id_cargo) " +
                "INNER JOIN Egresso e ON p.egresso_id = e.id_egresso)",
        nativeQuery = true)
    public Long quantitativoEgressos(@Param("id_cargo") Long idCargo);
}
