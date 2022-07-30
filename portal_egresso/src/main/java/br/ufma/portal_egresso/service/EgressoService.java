package br.ufma.portal_egresso.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import br.ufma.portal_egresso.entidade.Egresso;
//import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
//import br.ufma.portal_egresso.service.exceptions.ErroDepoimentoRunTime;
//import br.ufma.portal_egresso.service.exceptions.ErroEgressoRunTime;
//
//@Service
//public class EgressoService {
//
//  @Autowired
//  EgressoRepo repository;
//
//
//  public void deletarEgresso(Long id) {
//    Optional<Egresso> egresso = repository.findById(id);
//    if (!egresso.isPresent())
//      throw new ErroEgressoRunTime("Egresso inexistente");
//    repository.delete(egresso.get());
//  }
//
//  private void verificarId(Egresso egresso) {
//    if ((egresso == null) || (egresso.getId() == null))
//      throw new ErroEgressoRunTime("Egresso inválido");
//  }
//
//  private void verificarEgresso(Egresso egresso) {
//    //verificarId(egresso); comentei pois nao estava salvando Egresso pq o id sempre vem null o banco eh quem seta
//    if ((egresso.getNome() == null) || (egresso.getNome().equals("")))
//      throw new ErroEgressoRunTime("Nome deve ser informado");
//    if ((egresso.getCpf() == null) || (egresso.getCpf().equals("")))
//        throw new ErroDepoimentoRunTime("CPF deve ser informado");
//    if ((egresso.getEmail() == null) || (egresso.getEmail().equals("")))
//        throw new ErroDepoimentoRunTime("Email deve ser informado");
//  }
//}

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufma.portal_egresso.entidade.CursoEgresso;
import br.ufma.portal_egresso.entidade.ContatoEgresso;
import br.ufma.portal_egresso.entidade.Depoimento;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.ProfEgresso;
import br.ufma.portal_egresso.entidade.repositorio.ContatoEgressoRepo;
import br.ufma.portal_egresso.entidade.repositorio.CursoEgressoRepo;
import br.ufma.portal_egresso.entidade.repositorio.DepoimentoRepo;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
import br.ufma.portal_egresso.entidade.repositorio.ProfEgressoRepo;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@Service
public class EgressoService {

  @Autowired
  EgressoRepo repo;

  @Autowired
  DepoimentoRepo repoDepoimento;

  @Autowired
  CursoEgressoRepo repoCursoEgresso;

  @Autowired
  ContatoEgressoRepo repoContatoEgresso;

  @Autowired
  ProfEgressoRepo repoProfEgresso;

  @Autowired
  ProfEgressoService serviceProfEgresso;

  @Autowired
  CursoEgressoService serviceCursoEgresso;

  @Autowired
  ContatoEgressoService serviceContatoEgresso;


  public Egresso salvarEgresso(Egresso egresso) {
    verificarDadosEgresso(egresso);
    return repo.save(egresso);
  }

  public Egresso buscar_por_id(Long id){
    Optional<Egresso> egresso = repo.findById(id);
    if(egresso.isEmpty())
      throw new RegraNegocioRunTime("Erro ao buscar");

//    Egresso egressoFiltrado = Egresso.builder()
//            .id(egresso.get().getId())
//            .nome(egresso.get().getNome())
//            .cpf(egresso.get().getCpf())
//            .email(egresso.get().getEmail())
//            .resumo(egresso.get().getResumo()).build();
//    if(!egresso.get().getDepoimentos().isEmpty()){
//      egressoFiltrado.builder().depoimentos(egresso.get().getDepoimentos()).build();
//    }
//    if(!egresso.get().getContatos().isEmpty()){
//      egressoFiltrado.builder().contatos(egresso.get().getContatos()).build();
//    }
//    if(!egresso.get().getCursoEgresso().isEmpty()){
//      egressoFiltrado.builder().cursoEgresso(egresso.get().getCursoEgresso()).build();
//    }
//    if(!egresso.get().getProfEgressos().isEmpty()){
//      egressoFiltrado.builder().profEgressos(egresso.get().getProfEgressos()).build();
//    }


    return egresso.get();
  }

  public Egresso busca_dados_pagina_egresso(Long id){
    Optional<Egresso> egresso = repo.findById(id);
    if(egresso.isEmpty())
      throw new RegraNegocioRunTime("Erro ao buscar");

    return egresso.get();
  }

  public Egresso obterEgressoPorEmail(String email){
    return repo.findByEmail(email);
  }

  public List<Egresso> listar(){
    List<Egresso> egressos = repo.getAllEgressos();
    if(egressos.isEmpty()) throw new RegraNegocioRunTime("Erro ao buscar os egressos");
    return egressos;
  }

  public Egresso salvar(Egresso egresso) {
    verificarDadosEgresso(egresso);
    verificarDadosEgressoNovo(egresso);
    return repo.save(egresso);
  }

  public Egresso editar(Egresso egresso) {
    String email_aux = buscar_por_id(egresso.getId()).getEmail();
    if(!egresso.getEmail().equals(email_aux)){
      boolean email_existente = repo.existsByEmail(egresso.getEmail());
      if (email_existente)
        throw new RegraNegocioRunTime("Email informado já foi cadastrado");
    }

    verificarDadosEgresso(egresso);
    return repo.save(egresso);
  }

  public void remover(Egresso egresso) {
    verificarId(egresso);
    verificarDepoimento(egresso);
    verificarCursoEgresso(egresso);
    verificarContatoEgresso(egresso);
    verificarProfEgresso(egresso);
    repo.delete(egresso);
  }

  private void verificarId(Egresso egresso) {
    if ((egresso == null) || (egresso.getId() == null))
      throw new RegraNegocioRunTime("Egresso sem id");
  }

  private void verificarDadosEgresso(Egresso egresso) {
    if (egresso == null)
      throw new RegraNegocioRunTime("Um egresso válido deve ser informado");
    if ((egresso.getNome() == null) || (egresso.getNome().equals("")))
      throw new RegraNegocioRunTime("Nome do egresso deve ser informado");
    if ((egresso.getEmail() == null) || (egresso.getEmail().equals("")))
      throw new RegraNegocioRunTime("Email deve ser informado");
    if ((egresso.getCpf() == null) || (egresso.getCpf().equals("")))
      throw new RegraNegocioRunTime("Cpf deve ser informado");
  }

  private void verificarDadosEgressoNovo(Egresso egresso) {
    boolean email_existente = repo.existsByEmail(egresso.getEmail());
    if (email_existente)
      throw new RegraNegocioRunTime("Email informado já foi cadastrado");
    boolean cpf_existente = repo.existsByEmail(egresso.getCpf());
    if (cpf_existente)
      throw new RegraNegocioRunTime("Cpf informado já existe na base");
  }

  private void verificarDepoimento(Egresso egresso) {
    List<Depoimento> res = repoDepoimento.findByEgresso(egresso);
    if (!res.isEmpty())
      throw new RegraNegocioRunTime("Egresso informado possui depoimentos cadastrados");
  }
  private void verificarCursoEgresso(Egresso egresso) {
    List<CursoEgresso> res = repoCursoEgresso.findByEgresso(egresso);
    if (!res.isEmpty())
      throw new RegraNegocioRunTime("Egresso informado possui cursos cadastrados");
  }
  private void verificarContatoEgresso(Egresso egresso) {
    List<ContatoEgresso> res = repoContatoEgresso.findByEgresso(egresso);
    if (!res.isEmpty())
      throw new RegraNegocioRunTime("Egresso informado possui contatos cadastrados");
  }
  private void verificarProfEgresso(Egresso egresso) {
    List<ProfEgresso> res = repoProfEgresso.findByEgresso(egresso);
    if (!res.isEmpty())
      throw new RegraNegocioRunTime("Egresso informado possui profissões cadastradas");
  }

}