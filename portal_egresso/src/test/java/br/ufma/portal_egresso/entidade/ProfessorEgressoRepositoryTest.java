package br.ufma.portal_egresso.entidade;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import br.ufma.portal_egresso.entidade.repositorio.CargoRepo;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
import br.ufma.portal_egresso.entidade.repositorio.FaixaSalarioRepo;
import br.ufma.portal_egresso.entidade.repositorio.ProfEgressoRepo;

@ActiveProfiles("test")
@SpringBootTest
public class ProfessorEgressoRepositoryTest {

  @Autowired
  ProfEgressoRepo repository;
  @Autowired
  EgressoRepo repositoryEgresso;
  @Autowired
  FaixaSalarioRepo repositoryFaixaSalario;
  @Autowired
  CargoRepo repositoryCargo;

  private ProfEgresso cenario(){
    Egresso egresso = Egresso.builder()
                        .nome("Ric")
                        .email("ric@oi.com")
                        .cpf("12345678910")
                        .resumo("resumo")
                        .build();
    
    Cargo cargo = Cargo.builder()
                        .nome("chefe")
                        .descricao("chefe")
                        .build();

    FaixaSalario faixaSalario = FaixaSalario.builder()
                                  .descricao("faixa salarial")
                                  .build();

    ProfEgresso profEgresso = ProfEgresso.builder()
                              .empresa("ufma")
                              .descricao("teste")
                              .dataRegistro(LocalDate.of(2022, Month.MAY, 15))
                              .egresso(egresso)
                              .cargo(cargo)
                              .faixaSalario(faixaSalario)
                              .build();

    return profEgresso;
  }
  
  @Test
  public void deveSalvarProfEgresso() {
    
    //cenário
    ProfEgresso entity = cenario();

    //ação
    ProfEgresso retorno = repository.save(entity);

    //verificação
    Assertions.assertNotNull(retorno);
    Assertions.assertEquals(entity.getEmpresa(), retorno.getEmpresa());
    Assertions.assertEquals(entity.getDescricao(), retorno.getDescricao());
    Assertions.assertEquals(entity.getDataRegistro(), retorno.getDataRegistro());
  }

  @Test
  public void deveBuscarProfEgresso() {
    
    //cenário
    ProfEgresso profEgresso = cenario();
    
    Egresso egresso = repositoryEgresso.save(profEgresso.getEgresso());
    profEgresso.setEgresso(egresso);
    FaixaSalario faixaSalario = repositoryFaixaSalario.save(profEgresso.getFaixaSalario());
    profEgresso.setFaixaSalario(faixaSalario);
    Cargo cargo = repositoryCargo.save(profEgresso.getCargo());
    profEgresso.setCargo(cargo);
    

    ProfEgresso retorno = repository.save(profEgresso);
    
    //ação
    List<ProfEgresso> retList = repository.findByEgresso(egresso);
    ProfEgresso ret = retList.get(0);

    //verificação
    Assertions.assertNotNull(retorno);
    Assertions.assertEquals(profEgresso.getEmpresa(), ret.getEmpresa());
    Assertions.assertEquals(profEgresso.getDescricao(), ret.getDescricao());
    Assertions.assertEquals(profEgresso.getDataRegistro(), ret.getDataRegistro());
    Assertions.assertEquals(profEgresso.getEgresso().getId(), ret.getEgresso().getId());
    Assertions.assertEquals(profEgresso.getCargo().getId(), ret.getCargo().getId());
    Assertions.assertEquals(profEgresso.getFaixaSalario().getId(), ret.getFaixaSalario().getId());
  }

  @Test
  public void deveVerificarSeExisteProfEgressoPorEgresso() {
    
    //cenário
    ProfEgresso profEgresso = cenario();

    Egresso egresso = repositoryEgresso.save(profEgresso.getEgresso());
    profEgresso.setEgresso(egresso);
    FaixaSalario faixaSalario = repositoryFaixaSalario.save(profEgresso.getFaixaSalario());
    profEgresso.setFaixaSalario(faixaSalario);
    Cargo cargo = repositoryCargo.save(profEgresso.getCargo());
    profEgresso.setCargo(cargo);
    
    
    repository.save(profEgresso);
    
    //ação
    boolean ret = repository.existsByEgresso(egresso);

    //verificação
    Assertions.assertTrue(ret);
  }

  @Test
  public void deveVerificarDeleteProfEgressoPorEgresso() {
    
    //cenário
    ProfEgresso profEgresso = cenario();

    Egresso egresso = repositoryEgresso.save(profEgresso.getEgresso());
    profEgresso.setEgresso(egresso);
    FaixaSalario faixaSalario = repositoryFaixaSalario.save(profEgresso.getFaixaSalario());
    profEgresso.setFaixaSalario(faixaSalario);
    Cargo cargo = repositoryCargo.save(profEgresso.getCargo());
    profEgresso.setCargo(cargo);
    
    
    repository.save(profEgresso);
    
    //ação
    repository.deleteByEgresso(egresso);
    boolean ret = repository.existsByEgresso(egresso);

    //verificação
    Assertions.assertFalse(ret);
  }
}