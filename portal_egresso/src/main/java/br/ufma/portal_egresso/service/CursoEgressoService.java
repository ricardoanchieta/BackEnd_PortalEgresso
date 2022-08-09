package br.ufma.portal_egresso.service;

import java.util.List;
import java.util.Optional;

import br.ufma.portal_egresso.entidade.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.portal_egresso.entidade.Egresso;

import br.ufma.portal_egresso.entidade.CursoEgresso;
import br.ufma.portal_egresso.entidade.CursoEgressoPK;
import br.ufma.portal_egresso.entidade.repositorio.CursoEgressoRepo;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@Service
public class CursoEgressoService {

    @Autowired
    CursoEgressoRepo repo;

    public CursoEgresso salvar(CursoEgresso cursoEgresso) {
        verificarDadosCursoEgresso(cursoEgresso);
        return repo.save(cursoEgresso);
    }

    public CursoEgresso editar(CursoEgresso cursoEgresso) {
        verificarId(cursoEgresso);
        return salvar(cursoEgresso);
    }

    public void remover(CursoEgresso cursoEgresso) {
        verificarId(cursoEgresso);
        repo.delete(cursoEgresso);
    }

    public List<CursoEgresso>buscar_por_Egresso(Egresso egresso) {
        List<CursoEgresso> cursos = repo.findByEgresso(egresso);
        return cursos;
    }

    public CursoEgresso buscar_por_id(CursoEgressoPK id) {
        Optional<CursoEgresso> curso_egresso = repo.findById(id);
        return curso_egresso.get();
    }


    private void verificarId(CursoEgresso cursoEgresso) {
        if ((cursoEgresso == null) || (cursoEgresso.getId() == null))
            throw new RegraNegocioRunTime("curso egresso sem id");
    }

//    public long getQuantidadeEgressoPorCurso(Long curso_id){
//        return repo.getQuantDeEgressoPorCurso(curso_id);
//    }

    public  List<CursoEgresso>listarEgressosPorCurso(Curso curso){
        List<CursoEgresso> resp = repo.findByCurso(curso);
        return resp;
    }

    private void verificarDadosCursoEgresso(CursoEgresso cursoEgresso) {
        if (cursoEgresso == null)
            throw new RegraNegocioRunTime("Um curso válido deve ser informado");
        if((cursoEgresso.getEgresso() == null) || (cursoEgresso.getEgresso().equals("")))
            throw new RegraNegocioRunTime("Egresso não registrado");
        if((cursoEgresso.getDataInicio() == null) || (cursoEgresso.getDataInicio().equals("")))
            throw new RegraNegocioRunTime("Data de início do curso deve ser informado");
        if((cursoEgresso.getDataConclusao() == null) || (cursoEgresso.getDataConclusao().equals("")))
            throw new RegraNegocioRunTime("Data de conclusão do curso deve ser informado");
    }

    public List<CursoEgresso> listar(){
        List<CursoEgresso> egressosComCurso = repo.getAllEgressosComCurso();
        if(egressosComCurso.isEmpty()) throw new RegraNegocioRunTime("Erro ao buscar os egressos");
        return egressosComCurso;
    }

}