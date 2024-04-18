package br.com.fiap.unidades.service;

import br.com.fiap.unidades.dto.response.UnidadeResponse;
import br.com.fiap.unidades.dto.request.UnidadeRequest;
import br.com.fiap.unidades.entity.Unidade;
import br.com.fiap.unidades.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UnidadeService implements ServiceDTO<Unidade, UnidadeRequest, UnidadeResponse>{

    @Autowired
    private UnidadeRepository repo;

    @Autowired
    private UnidadeService unidadeService;

    @Override
    public Unidade toEntity(UnidadeRequest r) {

        return Unidade.builder()
                .nome(r.nome())
                .descricao(r.descricao())
                .sigla(r.sigla())
                .macro(repo.findById(r.macro().id()).orElse(null))
                .build();
    }

    @Override
    public UnidadeResponse toResponse(Unidade e) {
        return UnidadeResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .descricao(e.getDescricao())
                .sigla(e.getSigla())
                .macro(unidadeService.toResponse(e.getMacro()))
                .build();
    }

    @Override
    public List<Unidade> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Unidade> findAll(Example<Unidade> example) {
        return repo.findAll(example);
    }

    @Override
    public Unidade findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Unidade save(Unidade e) {
        return repo.save(e);
    }
}