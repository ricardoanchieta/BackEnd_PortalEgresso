package br.ufma.portal_egresso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.ufma.portal_egresso.entidade.Depoimento;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.repositorio.DepoimentoRepo;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@Service
public class DepoimentoService {

  @Autowired
  DepoimentoRepo repo;

  public Depoimento salvar(Depoimento depoimento) {
    verificarDadosDepoimento(depoimento);
    return repo.save(depoimento);
  }

  public Depoimento editar(Depoimento depoimento) {
    verificarId(depoimento);
    return salvar(depoimento);
  }

  public void verificarId(Depoimento depoimento) {
    if ((depoimento == null) || (depoimento.getId() == null))
      throw new RegraNegocioRunTime("depoimento sem id");
  }

  public void remover(Depoimento depoimento) {
    verificarId(depoimento);
    repo.delete(depoimento);
  }

  public List<Depoimento> getDepoimentosOrderByMostRecent(){
    return repo.findAll(Sort.by("data"));
  }

  public List<Depoimento> buscar_por_egresso(long egresso_id) {
    //List<Depoimento> depoimentos = repo.findByEgresso(egresso);
    List<Depoimento> depoimentos = repo.findByEgressoId(egresso_id);
    return depoimentos;
  }

  public List<Depoimento> depoimentosPorEgresso(Egresso egresso){
    if ((egresso == null) || (egresso.getId() == null))
      throw new RuntimeException("Egresso inexistente");
    List<Depoimento> depoimentos = repo.findByEgresso(egresso);
    if (depoimentos != null && depoimentos.size() >= 1)
      return depoimentos;
    else
      throw new RuntimeException("Egresso sem depoimentos");
  }

  public Depoimento buscarPorId(Long id) {
    if(id == null)  throw new RegraNegocioRunTime("depoimento não selecionado");
    Optional<Depoimento> depoimento = repo.findById(id);
    if(depoimento.isEmpty()) throw new RegraNegocioRunTime("depoimento não encontrado");
    return depoimento.get();
  }

  public void removerPorId(Long idDepoimento) {
    Optional<Depoimento> depoimento = repo.findById(idDepoimento);
    remover(depoimento.get());
  }

  private void verificarDadosDepoimento(Depoimento depoimento) {
    if (depoimento == null)
      throw new RegraNegocioRunTime("depoimento não informado");
    if ((depoimento.getEgresso() == null) || (depoimento.getEgresso().equals("")))
      throw new RegraNegocioRunTime("id do egresso não encontrado");
    if ((depoimento.getTexto() == null) || (depoimento.getTexto().equals("")))
      throw new RegraNegocioRunTime("Texto do depoimento deve ser informado");
    if ((depoimento.getData() == null) || (depoimento.getData().equals("")))
      throw new RegraNegocioRunTime("Data do depoimento não informado");
  }


}