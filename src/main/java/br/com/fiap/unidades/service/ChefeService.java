package br.com.fiap.unidades.service;

import br.com.fiap.unidades.dto.request.ChefeRequest;
import br.com.fiap.unidades.dto.response.ChefeResponse;
import br.com.fiap.unidades.entity.Chefe;
import br.com.fiap.unidades.repository.ChefeRepository;
import br.com.fiap.unidades.repository.UnidadeRepository;
import br.com.fiap.unidades.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefeService implements ServiceDTO<Chefe, ChefeRequest, ChefeResponse>{

    @Autowired
    private ChefeRepository chefeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UnidadeService unidadeService;

    @Override
    public Chefe toEntity(ChefeRequest r) {
        return Chefe.builder()
                .fim(r.fim())
                .inicio(r.inicio())
                .usuario(usuarioRepository.findById(r.usuario().id()).orElse(null))
                .substituto(r.substituto())
                .unidade(unidadeRepository.findById(r.unidade().id()).orElse(null))
                .build();
    }

    @Override
    public ChefeResponse toResponse(Chefe e) {

        return ChefeResponse.builder()
                .id(e.getId())
                .fim(e.getFim())
                .inicio(e.getInicio())
                .substituto(e.getSubstituto())
                .usuario(usuarioService.toResponse(e.getUsuario()))
                .unidade(unidadeService.toResponse(e.getUnidade()))
                .build();
    }

    @Override
    public List<Chefe> findAll() {
        return null;
    }

    @Override
    public List<Chefe> findAll(Example<Chefe> example) {
        return null;
    }

    @Override
    public Chefe findById(Long id) {
        return null;
    }

    @Override
    public Chefe save(Chefe e) {
        return null;
    }
}
