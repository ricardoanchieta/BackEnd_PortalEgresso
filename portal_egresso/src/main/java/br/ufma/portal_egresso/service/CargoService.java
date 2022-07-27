package br.ufma.portal_egresso.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.portal_egresso.entidade.Cargo;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.repositorio.CargoRepo;
import br.ufma.portal_egresso.service.exceptions.ErroCargoRunTime;

@Service
public class CargoService {
  @Autowired
  CargoRepo repository;

  public Cargo salvarCargo(Cargo cargo) {
    verificarCargo(cargo);
    return repository.save(cargo);
  }

  public Cargo editarCargo(Long id, String nome, String descricao) {
    Optional<Cargo> cargo = repository.findById(id);
    if (nome.equals(""))
      throw new ErroCargoRunTime("Nome inválido");
    if (descricao.equals(""))
      throw new ErroCargoRunTime("Descrição inválida");
    if (!cargo.isPresent())
      throw new ErroCargoRunTime("Cargo inexistente");
    cargo.get().setNome(nome);
    cargo.get().setDescricao(descricao);
    return repository.save(cargo.get());    
  }

  public void deletarCargo(Long id) {
    Optional<Cargo> cargo = repository.findById(id);
    if (!cargo.isPresent())
      throw new ErroCargoRunTime("Cargo inexistente");
    repository.delete(cargo.get());
  }
  
  public Cargo consultarCargo(Egresso egresso) {
    return repository.consultarCargo(egresso.getId());
  }

  public Long getQuantitativoEgressos(Cargo cargo) {
    return repository.quantitativoEgressos(cargo.getId());
  }

  private void verificarId(Cargo cargo) {
    if ((cargo == null) || (cargo.getId() == null))
      throw new ErroCargoRunTime("Cargo inválido");
  }

  private void verificarCargo(Cargo cargo) {
    verificarId(cargo);
    if ((cargo.getDescricao() == null) || (cargo.getDescricao().equals("")))
      throw new ErroCargoRunTime("Descrição não pode ser vazia");
    if ((cargo.getNome() == null) || (cargo.getNome().equals("")))
      throw new ErroCargoRunTime("Nome não pode ser vazio");
  }
}
