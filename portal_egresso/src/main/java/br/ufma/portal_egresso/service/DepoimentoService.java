package br.ufma.portal_egresso.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.portal_egresso.entidade.Depoimento;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.repositorio.DepoimentoRepo;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
import br.ufma.portal_egresso.service.exceptions.ErroDepoimentoRunTime;

@Service
public class DepoimentoService {
  
  @Autowired
  DepoimentoRepo repository;

  @Autowired
  EgressoRepo repoEgresso;

  public Depoimento salvarDepoimento(Depoimento depoimento) {
    verificarDepoimento(depoimento);
    return repository.save(depoimento);
  }

  public Depoimento editarDepoimento(Long id, String texto) {
    Optional<Depoimento> depoimento = repository.findById(id);
    if (texto.equals(""))
      throw new ErroDepoimentoRunTime("Texto inválido");
    if (!depoimento.isPresent())
      throw new ErroDepoimentoRunTime("Depoimento inexistente");
    depoimento.get().setTexto(texto);
    return repository.save(depoimento.get());    
  }

  public void deletarDepoimento(Long id) {
    Optional<Depoimento> depoimento = repository.findById(id);
    if (!depoimento.isPresent())
      throw new ErroDepoimentoRunTime("Depoimento inexistente");
    repository.delete(depoimento.get());
  }

  public List<Depoimento> depoimentosRecentes() {
    List<Depoimento> depoimentos = repository.findAll();
    Collections.sort(depoimentos, (a, b) -> b.getData().compareTo(a.getData()));
    return depoimentos;
  }

  public List<Depoimento> depoimentosPorEgresso(Egresso egresso) {
    if ((egresso == null) || (egresso.getId() == null))
      throw new ErroDepoimentoRunTime("Egresso inexistente");
    List<Depoimento> depoimentos = repository.findByEgresso(egresso);
    if (depoimentos != null && depoimentos.size() >= 1)
      return depoimentos;
    else
      throw new ErroDepoimentoRunTime("Egresso sem depoimentos");
  }

  private void verificarId(Depoimento depoimento) {
    if ((depoimento == null) || (depoimento.getId() == null))
      throw new ErroDepoimentoRunTime("Depoimento inválido");
  }

  private void verificarDepoimento(Depoimento depoimento) {
    verificarId(depoimento);
    if ((depoimento.getTexto() == null) || (depoimento.getTexto().equals("")))
      throw new ErroDepoimentoRunTime("Texto não pode ser vazio");
    if ((depoimento.getEgresso() == null))
      throw new ErroDepoimentoRunTime("Egresso deve ser informado");
  }
}
