package br.ufma.portal_egresso.entidade;
 
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.portal_egresso.entidade.repositorio.CursoRepo;

@SpringBootTest
public class CursoRepositoryTest {

    @Autowired
    CursoRepo repository;
    
    @Test
    public void deveSalvarCurso() {
      
      //cenário
      Curso curso = Curso.builder()
                            .nome("cnc da computacao")
                            .nivel("superior")
                            .build();
      //ação
       
      Curso retorno = repository.save(curso);
      
      //verificação
      Assertions.assertNotNull(retorno);
      Assertions.assertEquals(curso.getNome(), retorno.getNome());
      Assertions.assertEquals(curso.getNivel(), retorno.getNivel());

      repository.delete(retorno);
    }


    @Test
    public void deveBuscaCursoPorNome() {
      
      //cenário
      Curso curso = Curso.builder()
            .nome("cnc da computacao")
            .nivel("superior")
            .build();
       
      Curso retorno = repository.save(curso);
                      
      //ação
      List<Curso> ret = repository.findByNome("Teste");
      
      //verificação
      Assertions.assertNotNull(retorno);
      Assertions.assertTrue(ret.isEmpty());

      Curso retCurso = ret.get(0);


      Assertions.assertEquals(retorno.getId(), retCurso.getId());
      Assertions.assertEquals(retorno.getNome(), retCurso.getNome());
      Assertions.assertEquals(retorno.getNivel(), retCurso.getNivel());

      repository.delete(retorno);
    }

    @Test
    public void deveVerificarSeExisteCursoPorNome() {
      
      //cenário
      Curso curso = Curso.builder()
            .nome("cnc da computacao")
            .nivel("superior")
            .build();

       
      Curso retorno = repository.save(curso);
                      
      //ação
      boolean ret = repository.existsByNome("Teste");
      
      //verificação
      Assertions.assertNotNull(retorno);
      Assertions.assertFalse(ret);
    }

    @Test
    public void deveDeletarCursoPorNome() {
      
      //cenário
      Curso curso = Curso.builder()
            .nome("cnc da computacao")
            .nivel("superior")
            .build();     

       
      repository.save(curso);
                      
      //ação
      repository.deleteByNome("Teste");
      
      //verificação
      List<Curso> ret = repository.findByNome("Teste");
      Assertions.assertTrue(ret.isEmpty());
    }
}