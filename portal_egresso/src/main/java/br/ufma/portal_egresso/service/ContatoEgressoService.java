package br.ufma.portal_egresso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.portal_egresso.entidade.ContatoEgresso;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.repositorio.ContatoEgressoRepo;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@Service
public class ContatoEgressoService {

    @Autowired
    ContatoEgressoRepo repo;

    public ContatoEgresso salvar(ContatoEgresso contatoEgresso){
        verificarId(contatoEgresso);
        //verificarDadosContatoEgresso(contatoEgresso);
        return repo.save(contatoEgresso);
    }

    public void remover(ContatoEgresso contatoEgresso) {
        verificarId(contatoEgresso);
        repo.delete(contatoEgresso);
    }

    public List<ContatoEgresso>buscar_por_Egresso(Egresso egresso) {
        List<ContatoEgresso> contatos = repo.findByEgresso(egresso);
        return contatos;
    }

    private void verificarId(ContatoEgresso contatoEgresso) {
        if ((contatoEgresso == null) || (contatoEgresso.getId() == null))
            throw new RegraNegocioRunTime("Contato egresso sem id");
    }

    private void verificarDadosContatoEgresso(ContatoEgresso contatoEgresso) {
        if (contatoEgresso == null)
            throw new RegraNegocioRunTime("Um contato válido deve ser informado");
        if((contatoEgresso.getContato() == null) || (contatoEgresso.getContato().equals("")))
            throw new RegraNegocioRunTime("Um contato válido deve ser informado");
        if((contatoEgresso.getEgresso() == null) || (contatoEgresso.getEgresso().equals("")))
            throw new RegraNegocioRunTime("id do Egresso inválido no contato");
    }

}