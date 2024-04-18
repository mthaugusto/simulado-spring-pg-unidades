package br.com.fiap.unidades.resource;

import br.com.fiap.unidades.dto.response.ChefeResponse;
import br.com.fiap.unidades.dto.request.ChefeRequest;
import br.com.fiap.unidades.entity.Chefe;
import br.com.fiap.unidades.entity.Unidade;
import br.com.fiap.unidades.entity.Usuario;
import br.com.fiap.unidades.service.ChefeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/chefe")
public class ChefeResource implements ResourceDTO<ChefeRequest, ChefeResponse>{

    @Autowired
    private ChefeService service;

    @GetMapping
    public ResponseEntity<List<ChefeResponse>> findAll(
            @RequestParam(name="substituto", required = false) String substituto,
            @RequestParam(name="usuarioId", required = false) Long usuarioId,
            @RequestParam(name="unidadeId", required = false) Long unidadeId
    ){

        var chefe = Chefe.builder()
                .substituto(Boolean.valueOf(substituto))
                .usuario(Usuario.builder().id(usuarioId).build())
                .unidade(Unidade.builder().id(unidadeId).build())
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues();

        Example<Chefe> example = Example.of(chefe, matcher);

        List<Chefe> encontrados = service.findAll(example);

        List<ChefeResponse> respostas = encontrados.stream()
                .map(service::toResponse)
                .toList();

        return ResponseEntity.ok(respostas);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<ChefeResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById(id);
        var resposta = service.toResponse(encontrado);
        return ResponseEntity.ok(resposta);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<ChefeResponse> save(@RequestBody @Valid ChefeRequest r) {
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