package br.com.fiap.unidades.service;

import br.com.fiap.unidades.dto.response.PessoaResponse;
import br.com.fiap.unidades.dto.response.UsuarioResponse;
import br.com.fiap.unidades.dto.request.UsuarioRequest;
import br.com.fiap.unidades.entity.Pessoa;
import br.com.fiap.unidades.entity.Usuario;
import br.com.fiap.unidades.repository.PessoaRepository;
import br.com.fiap.unidades.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse>{

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PessoaRepository repoPessoa;

    @Autowired
    private PessoaService pessoaService;

    @Override
    public Usuario toEntity(UsuarioRequest r) {

        return Usuario.builder()
                .username(r.username())
                .password(r.passowrd())
                .pessoa(pessoaService.toEntity(r.pessoa()))
        .build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario e) {

        return UsuarioResponse.builder()
                .id(e.getId())
                .username(e.getUsername())
                .pessoa(pessoaService.toResponse(e.getPessoa()))
                .build();
    }

    @Override
    public List<Usuario> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Usuario> findAll(Example<Usuario> example) {
        return repo.findAll(example);
    }

    @Override
    public Usuario findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario e) {
        System.out.println(e);return repo.save(e);
    }
}