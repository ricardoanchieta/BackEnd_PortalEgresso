package br.ufma.portal_egresso.entidade;

import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EgressoRepositoryTest {
    @Autowired
    EgressoRepo repo;

    @Test
    public void deveSalvarEgresso(){
        //cenario
        Egresso novo = Egresso.builder().nome("Ricardo")
                                        .email("ric@oi.com")
                                        .cpf("2222")
                                        .resumo("Egresso de CP")
                                        .url_foto("www.foto.com")
                                        .build();

        //acao

        Egresso retorno = repo.save(novo);

        //varificacao
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(novo.getNome(), retorno.getNome());
        Assertions.assertEquals(novo.getEmail(), retorno.getEmail());
        Assertions.assertEquals(novo.getCpf(), retorno.getCpf());
        Assertions.assertEquals(novo.getResumo(), retorno.getResumo());
        Assertions.assertEquals(novo.getUrl_foto(), retorno.getUrl_foto());

        //rollback
        repo.delete(retorno);
        }
}
