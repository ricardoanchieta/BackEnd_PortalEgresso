package br.ufma.portal_egresso.service;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.portal_egresso.entidade.Contato;
import br.ufma.portal_egresso.entidade.ContatoEgresso;
import br.ufma.portal_egresso.entidade.ContatoEgressoPK;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.repositorio.ContatoEgressoRepo;
import br.ufma.portal_egresso.entidade.repositorio.ContatoRepo;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;

@SpringBootTest
public class ContatoEgressoServiceTest {
    @Autowired
    ContatoEgressoService service;

    @Autowired
    ContatoEgressoRepo repo;

    @Autowired
    EgressoRepo repoEgresso;

    @Autowired
    ContatoRepo repoContato;

    private Contato createCenarioContato(){
        return Contato.builder()
                        .nome("dud")
                        .url_logo("dud.com")
                        .build();
    }
    private Egresso createCenarioEgresso(){
        return Egresso.builder()
                        .nome("dud")
                        .cpf("123")
                        .email("dud@mail.com")
                        .resumo("dudududududududududud")
                        .build();
    }

    @Test
    public void deveVerificarSalvar() {
        // cenario
        List<Contato> contatos = repoContato.findByNomeContaining("nnnnn");
        Contato contato = contatos.get(0);
        List<Egresso> egressos = repoEgresso.findByNomeContaining("nnnnn");
        Egresso egresso = egressos.get(0);

        ContatoEgresso contatoEgresso = ContatoEgresso.builder()
                    .id(new ContatoEgressoPK(egresso.getId(), contato.getId()))
                    .contato(contato)
                    .egresso(egresso)
                    .descricao("nnnnn")
                    .build();

        // ação
        ContatoEgresso salvo = service.salvar(contatoEgresso);

        // verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(contatoEgresso.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(contatoEgresso.getContato(), salvo.getContato());
        Assertions.assertEquals(contatoEgresso.getEgresso(), salvo.getEgresso());
    }

    @Test
    public void deveVerificarRemover() {
        // cenario
        List<ContatoEgresso> contatoEgressos = repo.findByDescricao("nnnnn");
        ContatoEgresso contatoEgresso = contatoEgressos.get(0);
        Egresso eg = contatoEgresso.getEgresso();

        // ação
        service.remover(contatoEgresso);

        // verificação

    }
    
}
