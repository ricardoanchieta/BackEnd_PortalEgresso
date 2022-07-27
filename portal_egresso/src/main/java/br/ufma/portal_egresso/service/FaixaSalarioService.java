package br.ufma.portal_egresso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.portal_egresso.entidade.FaixaSalario;
import br.ufma.portal_egresso.entidade.repositorio.FaixaSalarioRepo;

@Service
public class FaixaSalarioService {
  
  @Autowired
  FaixaSalarioRepo repository;

  public Long getQuantitativoEgresso(FaixaSalario faixaSalario) {
    return repository.getQuantitativoEgresso(faixaSalario.getId());
  }
}
