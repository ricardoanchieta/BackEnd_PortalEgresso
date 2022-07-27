package br.ufma.portal_egresso.entidade;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import br.ufma.portal_egresso.entidade.repositorio.DepoimentoRepo;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;


@SpringBootTest
public class DepoimentoRepositoryTest {

    @Autowired
    DepoimentoRepo depoimentoRepo;
    @Autowired
    EgressoRepo egressoRepo;

    @Test
    public void deveCriarDepoimento() {

        //cenário
        Egresso novo = Egresso.builder().nome("Rafifa")
                .email("rafifa@oi.com")
                .cpf("9090")
                .resumo("Egresso de CP")
                .url_foto("www.foto.com")
                .build();
        Egresso retornoEgresso = egressoRepo.save(novo);

        Depoimento novoDepoimento = Depoimento.builder().texto("Algo")
                .data(LocalDate.of(2020, 1, 8))
                .egresso(retornoEgresso)
                .build();

        //acao
        Depoimento retornoDepoimento = depoimentoRepo.save(novoDepoimento);

        //verificacao
        Assertions.assertNotNull(retornoDepoimento);

        //rollback
        depoimentoRepo.delete(retornoDepoimento);
        egressoRepo.delete(retornoEgresso);
    }

    @Test
    public void deveRemoverDepoimento(){
        //cenario
        Egresso novo = Egresso.builder().nome("Ricardo")
                .email("ric@oi.com")
                .cpf("2222")
                .resumo("Egresso de CP")
                .url_foto("www.foto.com")
                .build();
        Egresso retornoEgresso = egressoRepo.save(novo);

        Depoimento novoDepoimento = Depoimento.builder().texto("Algo")
                .data(LocalDate.of(2020, 1, 8))
                .egresso(retornoEgresso)
                .build();

        //acao
        Depoimento retornoDepoimento = depoimentoRepo.save(novoDepoimento);
        Long id = retornoDepoimento.getId();
        depoimentoRepo.deleteById(id);

        //verificacao
        Assertions.assertNotNull(retornoDepoimento);

        //rollback
        Optional<Depoimento> temp = depoimentoRepo.findById(id);
        Assertions.assertFalse(temp.isPresent());
    }

    @Test
    public void deveBuscarDepoimento(){
        //cenario
        Egresso novo = Egresso.builder().nome("Ricardo")
                .email("ric@oi.com")
                .cpf("2222")
                .resumo("Egresso de CP")
                .url_foto("www.foto.com")
                .build();
        Egresso retornoEgresso = egressoRepo.save(novo);

        Depoimento novoDepoimento = Depoimento.builder().texto("Algo")
                .data(LocalDate.of(2020, 1, 8))
                .egresso(retornoEgresso)
                .build();
        Depoimento retornoDepoimento = depoimentoRepo.save(novoDepoimento);

        //acao
        Optional<Depoimento> temp = depoimentoRepo.findById(retornoDepoimento.getId());

        //verificacao
        Assertions.assertTrue(temp.isPresent());

        //rollback
        depoimentoRepo.delete(retornoDepoimento);
        egressoRepo.delete(retornoEgresso);
    }
}