package br.ufma.portal_egresso.entidade;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import br.ufma.portal_egresso.entidade.repositorio.CargoRepo;

@SpringBootTest
public class CargoRepositoryTest {

    @Autowired
    CargoRepo repository;
    
    @Test
    public void deveSalvarCargo(){
      
      //cenário
      Cargo cargo = Cargo.builder()
          .nome("chefe")
          .descricao("descricao")
          .build();

      //ação
      //  
      Cargo retorno = repository.save(cargo); 
      
      //verificação
      Assertions.assertNotNull(retorno);
      Assertions.assertEquals(cargo.getNome(), retorno.getNome());
      Assertions.assertEquals(cargo.getDescricao(), retorno.getDescricao());

      // repository.delete(retorno);
    }

    @Test
    public void deveBuscarCargoPorNome() {
      
      //cenário
      Cargo cargo = Cargo.builder()
        .nome("chefe")
        .descricao("descricao")
        .build();

      //ação
      //  
      Cargo retorno = repository.save(cargo); 
      List <Cargo>  retList = repository.findByNome(cargo.getNome());
      
      //verificação
      Assertions.assertFalse(retList.isEmpty());

      Cargo ret = retList.get(0);

      Assertions.assertEquals(cargo.getNome(), ret.getNome());
      Assertions.assertEquals(cargo.getDescricao(), ret.getDescricao());

      //rollback
      repository.delete(retorno);
    }

    @Test
    public void deveVerificarSeExisteCargo() {
      
      //cenário
      Cargo cargo = Cargo.builder()
        .nome("chefe")
        .descricao("descricao")
        .build();

      //ação
      Cargo retorno = repository.save(cargo); 
      boolean ret = repository.existsByNome(cargo.getNome());
      
      //verificação
      Assertions.assertTrue(ret);

      //rollback
      repository.delete(retorno);
    }

}