package br.ufma.portal_egresso.entidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import br.ufma.portal_egresso.entidade.repositorio.FaixaSalarioRepo;


@SpringBootTest
public class FaixaSalarioRepositoryTest {

    @Autowired
    FaixaSalarioRepo repository;
    
    @Test
    public void deveSalvarFaixaSalario() {
      
      //cenário
      FaixaSalario novafaixaSalario = FaixaSalario.builder().descricao("vinte mil reis").build();
      //ação
      FaixaSalario retorno = repository.save(novafaixaSalario);
      
      //verificação
      Assertions.assertNotNull(retorno);
      Assertions.assertEquals(novafaixaSalario.getDescricao(), retorno.getDescricao());

      //rollback
      //repository.delete(retorno);
    }



}