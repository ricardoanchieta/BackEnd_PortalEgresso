package br.ufma.portal_egresso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
import br.ufma.portal_egresso.service.exceptions.ErroDepoimentoRunTime;
import br.ufma.portal_egresso.service.exceptions.ErroEgressoRunTime;

@Service
public class EgressoService {
  
  @Autowired
  EgressoRepo repository;

  public Egresso salvarEgresso(Egresso egresso) {
    verificarEgresso(egresso);
    return repository.save(egresso);
  }

  public void deletarEgresso(Long id) {
    Optional<Egresso> egresso = repository.findById(id);
    if (!egresso.isPresent())
      throw new ErroEgressoRunTime("Egresso inexistente");
    repository.delete(egresso.get());
  }

  private void verificarId(Egresso egresso) {
    if ((egresso == null) || (egresso.getId() == null))
      throw new ErroEgressoRunTime("Egresso inválido");
  }

  private void verificarEgresso(Egresso egresso) {
    //verificarId(egresso); comentei pois nao estava salvando Egresso pq o id sempre vem null o banco eh quem seta
    if ((egresso.getNome() == null) || (egresso.getNome().equals("")))
      throw new ErroEgressoRunTime("Nome deve ser informado");
    if ((egresso.getCpf() == null) || (egresso.getCpf().equals("")))
        throw new ErroDepoimentoRunTime("CPF deve ser informado");
    if ((egresso.getEmail() == null) || (egresso.getEmail().equals("")))
        throw new ErroDepoimentoRunTime("Email deve ser informado");
  }
}
