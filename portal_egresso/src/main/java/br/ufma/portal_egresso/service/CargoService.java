package br.ufma.portal_egresso.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import br.ufma.portal_egresso.entidade.Cargo;
//import br.ufma.portal_egresso.entidade.Egresso;
//import br.ufma.portal_egresso.entidade.repositorio.CargoRepo;
//import br.ufma.portal_egresso.service.exceptions.ErroCargoRunTime;
//
//@Service
//public class CargoService {
//  @Autowired
//  CargoRepo repository;
//
//  public Cargo salvarCargo(Cargo cargo) {
//    verificarCargo(cargo);
//    return repository.save(cargo);
//  }
//
//  public Cargo editarCargo(Long id, String nome, String descricao) {
//    Optional<Cargo> cargo = repository.findById(id);
//    if (nome.equals(""))
//      throw new ErroCargoRunTime("Nome inválido");
//    if (descricao.equals(""))
//      throw new ErroCargoRunTime("Descrição inválida");
//    if (!cargo.isPresent())
//      throw new ErroCargoRunTime("Cargo inexistente");
//    cargo.get().setNome(nome);
//    cargo.get().setDescricao(descricao);
//    return repository.save(cargo.get());
//  }
//
//  public void deletarCargo(Long id) {
//    Optional<Cargo> cargo = repository.findById(id);
//    if (!cargo.isPresent())
//      throw new ErroCargoRunTime("Cargo inexistente");
//    repository.delete(cargo.get());
//  }
//
//  public Cargo consultarCargo(Egresso egresso) {
//    return repository.consultarCargo(egresso.getId());
//  }
//
//  public Long getQuantitativoEgressos(Cargo cargo) {
//    return repository.quantitativoEgressos(cargo.getId());
//  }
//
//  private void verificarId(Cargo cargo) {
//    if ((cargo == null) || (cargo.getId() == null))
//      throw new ErroCargoRunTime("Cargo inválido");
//  }
//
//  private void verificarCargo(Cargo cargo) {
//    verificarId(cargo);
//    if ((cargo.getDescricao() == null) || (cargo.getDescricao().equals("")))
//      throw new ErroCargoRunTime("Descrição não pode ser vazia");
//    if ((cargo.getNome() == null) || (cargo.getNome().equals("")))
//      throw new ErroCargoRunTime("Nome não pode ser vazio");
//  }
//}


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.portal_egresso.entidade.Cargo;
import br.ufma.portal_egresso.entidade.ProfEgresso;
import br.ufma.portal_egresso.entidade.repositorio.CargoRepo;
import br.ufma.portal_egresso.entidade.repositorio.ProfEgressoRepo;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;


@Service
public class CargoService {

  @Autowired
  CargoRepo repo;

  @Autowired
  ProfEgressoRepo repoProfEgresso;

  public List<Cargo> listar(){
    return repo.obterCargos();
  }

  public Cargo salvar(Cargo cargo) {
    verificarDadosCargo(cargo);
    return repo.save(cargo);
  }

  public Cargo editar(Cargo cargo) {
    verificarId(cargo);
    verificarProfEgresso(cargo);
    return salvar(cargo);
  }

  public void remover(Long id) {
    if(id == null)  throw new RegraNegocioRunTime("cargo não encontrado");
    Optional<Cargo> cargo = repo.findById(id);
    verificarProfEgresso(cargo.get());
    repo.deleteById(id);
  }

  public Cargo buscarPorId(Long id) {
    if(id == null)  throw new RegraNegocioRunTime("cargo não selecionado");
    Optional<Cargo> cargo = repo.findById(id);
    if(cargo.isEmpty()) throw new RegraNegocioRunTime("cargo não encontrado");
    return cargo.get();
  }

  private void verificarId(Cargo cargo) {
    if ((cargo == null) || (cargo.getId() == null))
      throw new RegraNegocioRunTime("cargo sem id");
  }

  private void verificarDadosCargo(Cargo cargo) {
    if (cargo == null)
      throw new RegraNegocioRunTime("cargo não inserido");
    if ((cargo.getNome() == null) || (cargo.getNome().equals("")))
      throw new RegraNegocioRunTime("Nome do cargo obrigatório");
    if ((cargo.getDescricao() == null) || (cargo.getDescricao().equals("")))
      throw new RegraNegocioRunTime("Descrição do cargo obrigatório");
  }

  private void verificarProfEgresso(Cargo cargo) {
    List<ProfEgresso> res = repoProfEgresso.findByCargo(cargo);
    if (!res.isEmpty())
      throw new RegraNegocioRunTime("cargo informado está sendo utilizado");
  }

}