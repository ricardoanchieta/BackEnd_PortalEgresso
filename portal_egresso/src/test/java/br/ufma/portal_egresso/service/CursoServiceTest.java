package br.ufma.portal_egresso.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.portal_egresso.entidade.Curso;
import br.ufma.portal_egresso.entidade.repositorio.CursoRepo;


@SpringBootTest
public class CursoServiceTest {
    @Autowired
    CursoRepo repo;

    @Autowired
    CursoService service;

    private Curso createCenarioCurso(){
        return Curso.builder()
                        .nome("departamento de informatica")
                        .nivel("gerente")
                        .build();
    }

    @Test
    public void deveVerificarSalvar() {
        // cenario
        Curso curso = createCenarioCurso();

        // ação
        Curso salvo = service.salvar(curso);

        // verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso.getNome(), salvo.getNome());
        Assertions.assertEquals(curso.getNivel(), salvo.getNivel());
    }


}
