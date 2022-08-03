package br.ufma.portal_egresso.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.portal_egresso.entidade.Curso;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.FaixaSalario;
import br.ufma.portal_egresso.entidade.Cargo;
import br.ufma.portal_egresso.entidade.Contato;
import br.ufma.portal_egresso.entidade.ContatoEgresso;
import br.ufma.portal_egresso.entidade.ProfEgresso;
import br.ufma.portal_egresso.entidade.CursoEgressoPK;
import br.ufma.portal_egresso.entidade.CursoEgresso;
import br.ufma.portal_egresso.entidade.ContatoEgressoPK;
import br.ufma.portal_egresso.entidade.repositorio.CursoRepo;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
import br.ufma.portal_egresso.entidade.repositorio.FaixaSalarioRepo;
import br.ufma.portal_egresso.entidade.repositorio.CargoRepo;
import br.ufma.portal_egresso.entidade.repositorio.ContatoRepo;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;


@SpringBootTest
public class EgressoServiceTest {
    
    @Autowired
    EgressoService service;

    @Autowired
    EgressoRepo repo;
    @Autowired
    FaixaSalarioRepo repoFaixaSalario;
    @Autowired
    CargoRepo repoCargo;
    @Autowired
    ContatoRepo repoContato;
    @Autowired
    CursoRepo repoCurso;

    private Egresso createCenario(){
        return Egresso.builder()
            .nome("uma vez")
            .email("uma vez@email.com") // email unico
            .cpf("uma vez") // cpf unico
            .resumo("uma vez")
            .url_foto("http://seuma vezrvice")
            .build();
    }
    private Curso createCenarioCurso(){
        return Curso.builder()
            .nome("uma vez")
            .nivel("uma vez")
            .build();
    }
    private Cargo createCenarioCargo(){
        return Cargo.builder()
            .nome("uma vez")
            .descricao("uma vez service")
            .build();
    }
    private FaixaSalario createCenarioFaixaSalario(){
        return FaixaSalario.builder()
            .descricao("uma vez service")
            .build();
    }
    private Contato createCenarioContato(){
        return Contato.builder()
            .nome("uma vez")
            .url_logo("uma vez")
            .build();
    }
    private CursoEgresso createCenarioCursoEgresso(Curso curso, Egresso egresso){
        return CursoEgresso.builder()
            .id(new CursoEgressoPK(egresso.getId(), curso.getId()))
            .curso(curso)
            .egresso(egresso)
            .dataConclusao(LocalDate.of(2005, 6, 12))
            .dataInicio(LocalDate.of(2002, 6, 12))
            .build();
    }
    private ProfEgresso createCenarioProfEgresso(Egresso egresso, Cargo cargo, FaixaSalario faixaSalario){
        return ProfEgresso.builder()
            .empresa("uma vez service")
            .descricao("uma vez service")
            .dataRegistro(LocalDate.of(2020, 1, 8))
            .egresso(egresso)
            .cargo(cargo)
            .faixaSalario(faixaSalario)
            .build();
    }

    private ContatoEgresso createCenarioContatoEgresso(Egresso egresso, Contato contato){
        return ContatoEgresso.builder()
            .id(new ContatoEgressoPK(egresso.getId(), contato.getId()))
            .egresso(egresso)
            .contato(contato)
            .descricao("uma vez")
            .build();
    }

    @Test
    public void deveVerificarRemover() {
        // cenario
        String email = "uma vez@email.com";
        Egresso egresso = repo.findByEmail(email);
        if(egresso == null)
            throw new RegraNegocioRunTime("Erro: Email não existe");

        // ação
        Assertions.assertThrows(
            RegraNegocioRunTime.class, 
                            () -> service.remover(egresso), 
                            "egresso possui depoimento cadastrado");
    }

}
