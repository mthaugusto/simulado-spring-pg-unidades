package br.com.fiap.unidades.resource;


import br.com.fiap.unidades.dto.response.UsuarioResponse;
import br.com.fiap.unidades.dto.request.UsuarioRequest;
import br.com.fiap.unidades.entity.Pessoa;
import br.com.fiap.unidades.entity.Tipo;
import br.com.fiap.unidades.entity.Usuario;
import br.com.fiap.unidades.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource implements ResourceDTO<UsuarioRequest, UsuarioResponse>{

    @Autowired
    private UsuarioService service;
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "pessoaId", required = false) Long pessoaId,
            @RequestParam(name = "pessoaNome", required = false) String pessoaNome,
            @RequestParam(name = "pessoaSobrenome", required = false) String pessoaSobrenome,
            @RequestParam(name = "pessoaNascimento", required = false) LocalDate pessoaNascimento,
            @RequestParam(name = "pessoaTipo", required = false) Tipo pessoaTipo,
            @RequestParam(name = "pessoaEmail", required = false) String pessoaEmail
    ) {

        var usuario = Usuario.builder()
                .username(username)
                .pessoa(Pessoa.builder().id(pessoaId).build())
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Usuario> example = Example.of(usuario, matcher);

        List<Usuario> encontrados = service.findAll(example);

        List<UsuarioResponse> respostas = encontrados.stream()
                .map(service::toResponse)
                .toList();

        return ResponseEntity.ok(respostas);
    }


    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById(id);
        var resposta = service.toResponse(encontrado);
        return ResponseEntity.ok(resposta);
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody @Valid UsuarioRequest r) {
        var entity = service.toEntity(r);
        var saved = service.save(entity);
        var resposta = service.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(resposta);
    }
}