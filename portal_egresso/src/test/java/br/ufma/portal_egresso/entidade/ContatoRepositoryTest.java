package br.ufma.portal_egresso.entidade;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import br.ufma.portal_egresso.entidade.repositorio.ContatoRepo;


@ActiveProfiles("test")
@SpringBootTest
public class ContatoRepositoryTest {

    @Autowired
    ContatoRepo repository;
    @Test
    public void deveSalvarContato() {
      
      //cenário
      Contato contato = Contato.builder()
          .nome("Zap")
          .url_logo("canva.com")
          .build();

      //ação
      Contato retorno = repository.save(contato);  //salva?
      
      //verificação
      Assertions.assertNotNull(retorno);
      Assertions.assertEquals(contato.getNome(), retorno.getNome());
      Assertions.assertEquals(contato.getUrl_logo(), retorno.getUrl_logo());

      //rollback
      repository.delete(retorno);
    }


    @Test
    public void deveBuscarContato() {
      
      //cenário
      Contato contato = Contato.builder()
          .nome("Zap")
          .url_logo("canva.com")
          .build();

      //ação
      Contato retorno = repository.save(contato);
      List <Contato>  retList = repository.findByNome(contato.getNome());
      
      //verificação
      Assertions.assertFalse(retList.isEmpty());

      Contato ret = retList.get(0);

      Assertions.assertEquals(contato.getNome(), ret.getNome());
      Assertions.assertEquals(contato.getUrl_logo(), ret.getUrl_logo());

      //rollback
      repository.delete(retorno);
    }

    @Test
    public void deveVerificarSeExisteContato() {
      
      //cenário
      Contato contato = Contato.builder()
          .nome("Zap")
          .url_logo("canva.com")
          .build();

      //ação
       
      Contato retorno = repository.save(contato);  //salva?
      boolean  ret = repository.existsByNome(contato.getNome());
      
      //verificação
      Assertions.assertTrue(ret);

      //rollback
      repository.delete(retorno);
    }
}