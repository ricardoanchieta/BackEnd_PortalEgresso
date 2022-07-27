package br.ufma.portal_egresso.controller;

import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.dto.EgressoDTO;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
import br.ufma.portal_egresso.service.EgressoService;
import br.ufma.portal_egresso.service.exceptions.ErroEgressoRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/egresso")

public class EgressoController {

    @Autowired
    EgressoService service;

    @PostMapping("/salvar")
    public ResponseEntity salvarEgresso (@RequestBody EgressoDTO request){
        Egresso egresso = Egresso.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .resumo(request.getResumo())
                .url_foto(request.getUrl_foto())
                .build();
        try {
            Egresso salvo = service.salvarEgresso(egresso);
            return new ResponseEntity(salvo, HttpStatus.CREATED);
        }catch (ErroEgressoRunTime ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteEgresso(@RequestBody EgressoDTO request){
        Long id = request.getId();

        try{
            service.deletarEgresso(id);
            return new ResponseEntity("Egresso deletado com sucesso",HttpStatus.ACCEPTED);
        }catch (ErroEgressoRunTime ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
